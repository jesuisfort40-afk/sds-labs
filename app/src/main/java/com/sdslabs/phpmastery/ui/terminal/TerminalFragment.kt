package com.sdslabs.phpmastery.ui.terminal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sdslabs.phpmastery.MainActivity
import com.sdslabs.phpmastery.databinding.FragmentTerminalBinding
import com.sdslabs.phpmastery.model.LessonData
import kotlinx.coroutines.*

class TerminalFragment : Fragment() {

    private var _binding: FragmentTerminalBinding? = null
    private val binding get() = _binding!!
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTerminalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupChallenges()

        binding.etPhpEditor.setText("<?php\n// Votre code PHP ici\n\$message = \"Bonjour SDS LABS!\";\necho \$message . \"\\n\";\n\n// Boucle for\nfor (\$i = 1; \$i <= 5; \$i++) {\n    echo \"Ligne \" . \$i . \"\\n\";\n}\n?>")

        binding.btnRun.setOnClickListener { runPHP() }
        binding.btnClear.setOnClickListener {
            binding.etPhpEditor.setText("<?php\n// Votre code PHP ici\n\n?>")
            binding.outputCard.visibility = View.GONE
        }
    }

    private fun setupChallenges() {
        binding.challengesContainer.removeAllViews()
        LessonData.challenges.forEachIndexed { index, challenge ->
            val item = LayoutInflater.from(requireContext())
                .inflate(com.sdslabs.phpmastery.R.layout.item_challenge_card, binding.challengesContainer, false)

            item.findViewById<android.widget.TextView>(com.sdslabs.phpmastery.R.id.tvChallengeTitle).text = challenge.title
            item.findViewById<android.widget.TextView>(com.sdslabs.phpmastery.R.id.tvChallengeDesc).text = challenge.description

            val tvDiff = item.findViewById<android.widget.TextView>(com.sdslabs.phpmastery.R.id.tvDifficulty)
            tvDiff.text = "● ${challenge.difficulty} — ${challenge.xpReward} XP"
            tvDiff.setTextColor(
                android.graphics.Color.parseColor(
                    when (challenge.difficulty) {
                        "FACILE" -> "#10b981"
                        "MOYEN" -> "#f59e0b"
                        else -> "#ef4444"
                    }
                )
            )

            item.setOnClickListener {
                binding.etPhpEditor.setText(challenge.starterCode)
                binding.outputCard.visibility = View.GONE
                Toast.makeText(requireContext(), "Défi chargé : ${challenge.title}", Toast.LENGTH_SHORT).show()
            }

            binding.challengesContainer.addView(item)
        }
    }

    private fun runPHP() {
        val code = binding.etPhpEditor.text.toString()
        binding.outputCard.visibility = View.VISIBLE
        binding.tvOutput.text = "$ php -r \"...\"\n"

        scope.launch {
            delay(400)
            val result = simulatePHP(code)
            binding.tvOutput.text = "$ php -r \"...\"\n$result"
            val xpManager = (requireActivity() as MainActivity).xpManager
            xpManager.addXP(5)
            (requireActivity() as MainActivity).updateXPDisplay()
        }
    }

    // FIX: Much improved PHP simulator - handles real output patterns
    private fun simulatePHP(code: String): String {
        val cleanCode = code.replace(Regex("<\\?php|\\?>"), "").trim()
        val output = StringBuilder()

        try {
            // FizzBuzz detection
            if (cleanCode.contains("FizzBuzz") || cleanCode.contains("fizzbuzz", true)) {
                for (i in 1..20) {
                    output.appendLine(
                        when {
                            i % 15 == 0 -> "FizzBuzz"
                            i % 3 == 0 -> "Fizz"
                            i % 5 == 0 -> "Buzz"
                            else -> i.toString()
                        }
                    )
                }
                output.appendLine("... (20 premières lignes sur 100)")
                return output.toString()
            }

            // Fibonacci detection
            if (cleanCode.contains("fibonacci", true)) {
                fun fib(n: Int): Int = if (n <= 1) n else fib(n - 1) + fib(n - 2)
                for (i in 0 until 10) output.append("${fib(i)} ")
                output.appendLine()
                return output.toString()
            }

            // Parse variables
            val vars = mutableMapOf<String, String>()
            val varPattern = Regex("""\$(\w+)\s*=\s*["']([^"']+)["'];""")
            varPattern.findAll(cleanCode).forEach {
                vars[it.groupValues[1]] = it.groupValues[2]
            }

            val numPattern = Regex("""\$(\w+)\s*=\s*(\d+);""")
            numPattern.findAll(cleanCode).forEach {
                vars[it.groupValues[1]] = it.groupValues[2]
            }

            // Parse for loops
            val forPattern = Regex("""for\s*\(\s*\$(\w+)\s*=\s*(\d+);\s*\$\1\s*([<>=!]+)\s*(\d+);\s*\$\1(\+\+|--|[+-]=\s*\d+)\s*\)\s*\{([^}]+)\}""", RegexOption.DOT_MATCHES_ALL)
            forPattern.findAll(cleanCode).forEach { match ->
                var i = match.groupValues[2].toInt()
                val limit = match.groupValues[4].toInt()
                val op = match.groupValues[3]
                val incExpr = match.groupValues[5]
                val body = match.groupValues[6]

                var count = 0
                while (count < 100) {
                    val cond = when (op) {
                        "<=" -> i <= limit
                        "<" -> i < limit
                        ">=" -> i >= limit
                        ">" -> i > limit
                        else -> false
                    }
                    if (!cond) break

                    val lineOut = processEchoLine(body, vars + mapOf(match.groupValues[1] to i.toString()))
                    if (lineOut.isNotEmpty()) output.append(lineOut)

                    when {
                        incExpr == "++" -> i++
                        incExpr == "--" -> i--
                        incExpr.startsWith("+=") -> i += incExpr.removePrefix("+=").trim().toIntOrNull() ?: 1
                        incExpr.startsWith("-=") -> i -= incExpr.removePrefix("-=").trim().toIntOrNull() ?: 1
                    }
                    count++
                }
            }

            // Simple echo statements (outside loops)
            if (output.isEmpty()) {
                val echoLines = processEchoLine(cleanCode, vars)
                if (echoLines.isNotEmpty()) output.append(echoLines)
            }

            return if (output.isNotEmpty()) output.toString()
            else "Exécution réussie (aucun output)"

        } catch (e: Exception) {
            return "Erreur: ${e.message}"
        }
    }

    private fun processEchoLine(code: String, vars: Map<String, String>): String {
        val result = StringBuilder()
        val echoPattern = Regex("""echo\s+(.+?);""")
        echoPattern.findAll(code).forEach { match ->
            var val_ = match.groupValues[1]
            // Replace variables
            vars.forEach { (k, v) -> val_ = val_.replace("\$$k", v) }
            // Remove quotes, handle concatenation and newlines
            val_ = val_.replace(Regex("""["']"""), "")
                .replace(Regex("""\s*\.\s*"""), "")
                .replace("\\n", "\n")
                .trim()
            result.append(val_)
        }
        return result.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
        _binding = null
    }
}
