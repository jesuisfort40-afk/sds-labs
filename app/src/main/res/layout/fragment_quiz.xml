package com.sdslabs.phpmastery.ui.quiz

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sdslabs.phpmastery.MainActivity
import com.sdslabs.phpmastery.R
import com.sdslabs.phpmastery.databinding.FragmentQuizBinding
import com.sdslabs.phpmastery.model.LessonData
import com.sdslabs.phpmastery.model.QuizQuestion

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private var currentQuestionIndex = 0
    private var score = 0
    private var answered = false
    private val questions = LessonData.quizQuestions
    private val letters = listOf("A", "B", "C", "D")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetQuiz()
    }

    private fun resetQuiz() {
        currentQuestionIndex = 0
        score = 0
        answered = false
        showQuestion()
        binding.quizResultLayout.visibility = View.GONE
        binding.quizQuestionLayout.visibility = View.VISIBLE
    }

    private fun showQuestion() {
        if (currentQuestionIndex >= questions.size) {
            showResult()
            return
        }

        val q = questions[currentQuestionIndex]
        answered = false

        // Progress
        val pct = (currentQuestionIndex.toFloat() / questions.size * 100).toInt()
        binding.quizProgressBar.progress = pct
        binding.tvQuizProgress.text = "${currentQuestionIndex + 1} / ${questions.size}"
        binding.tvQuestionNumber.text = "QUESTION ${currentQuestionIndex + 1}"
        binding.tvQuestion.text = q.question

        // Options
        val optionViews = listOf(
            binding.option0,
            binding.option1,
            binding.option2,
            binding.option3
        )

        optionViews.forEachIndexed { i, optionView ->
            if (i < q.options.size) {
                optionView.root.visibility = View.VISIBLE
                optionView.tvOptionLetter.text = letters[i]
                optionView.tvOptionText.text = q.options[i]
                resetOptionStyle(optionView.root)
                optionView.root.setOnClickListener {
                    if (!answered) selectOption(i, q, optionViews)
                }
            } else {
                optionView.root.visibility = View.GONE
            }
        }

        binding.feedbackBox.visibility = View.GONE
        binding.btnNextQuestion.visibility = View.GONE
    }

    private fun selectOption(selectedIdx: Int, q: QuizQuestion, optionViews: List<com.sdslabs.phpmastery.databinding.ItemQuizOptionBinding>) {
        answered = true

        optionViews.forEach { it.root.isClickable = false }

        if (selectedIdx == q.correctIndex) {
            setOptionStyle(optionViews[selectedIdx].root, "correct")
            score++
            val xpManager = (requireActivity() as MainActivity).xpManager
            xpManager.addXP(15)
            (requireActivity() as MainActivity).updateXPDisplay()
            binding.feedbackTitle.text = "✅ Correct !"
            binding.feedbackTitle.setTextColor(Color.parseColor("#10b981"))
            binding.feedbackBox.setBackgroundColor(Color.parseColor("#0A1F14"))
        } else {
            setOptionStyle(optionViews[selectedIdx].root, "wrong")
            setOptionStyle(optionViews[q.correctIndex].root, "correct")
            binding.feedbackTitle.text = "❌ Incorrect"
            binding.feedbackTitle.setTextColor(Color.parseColor("#ef4444"))
            binding.feedbackBox.setBackgroundColor(Color.parseColor("#1A0A0A"))
        }

        binding.feedbackExplanation.text = q.explanation
        binding.feedbackBox.visibility = View.VISIBLE

        binding.btnNextQuestion.visibility = View.VISIBLE
        binding.btnNextQuestion.text = if (currentQuestionIndex < questions.size - 1)
            "Question suivante →"
        else
            "Voir les résultats ✓"

        binding.btnNextQuestion.setOnClickListener {
            currentQuestionIndex++
            showQuestion()
        }
    }

    private fun resetOptionStyle(view: View) {
        view.setBackgroundColor(Color.parseColor("#111827"))
        view.elevation = 0f
    }

    private fun setOptionStyle(view: View, type: String) {
        when (type) {
            "correct" -> view.setBackgroundColor(Color.parseColor("#0A2A1A"))
            "wrong" -> view.setBackgroundColor(Color.parseColor("#2A0A0A"))
            "selected" -> view.setBackgroundColor(Color.parseColor("#0A1929"))
        }
    }

    private fun showResult() {
        binding.quizQuestionLayout.visibility = View.GONE
        binding.quizResultLayout.visibility = View.VISIBLE

        val pct = (score.toFloat() / questions.size * 100).toInt()
        val stars = when {
            pct >= 80 -> "⭐⭐⭐"
            pct >= 50 -> "⭐⭐"
            else -> "⭐"
        }

        binding.tvResultStars.text = stars
        binding.tvResultScore.text = "$pct%"
        binding.tvResultDetail.text = "$score/${questions.size} bonnes réponses"
        binding.tvResultMessage.text = when {
            pct >= 80 -> "Excellent ! Vous maîtrisez ce module. 🎉"
            pct >= 50 -> "Bien ! Relisez les points manqués."
            else -> "Continuez à pratiquer, vous y arriverez !"
        }

        if (pct >= 80) {
            val xpManager = (requireActivity() as MainActivity).xpManager
            xpManager.addXP(50)
            (requireActivity() as MainActivity).updateXPDisplay()
            binding.tvResultXp.text = "+50 XP Bonus !"
            binding.tvResultXp.visibility = View.VISIBLE
        } else {
            binding.tvResultXp.visibility = View.GONE
        }

        // FIX: Use proper navigation, not JS querySelectorAll reference
        binding.btnRetryQuiz.setOnClickListener { resetQuiz() }
        binding.btnGoTerminal.setOnClickListener {
            (requireActivity() as MainActivity).navigateTo(R.id.nav_terminal)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
