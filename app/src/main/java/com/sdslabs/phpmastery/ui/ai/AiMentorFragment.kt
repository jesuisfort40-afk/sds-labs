package com.sdslabs.phpmastery.ui.ai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdslabs.phpmastery.MainActivity
import com.sdslabs.phpmastery.databinding.FragmentAiMentorBinding
import com.sdslabs.phpmastery.model.ChatMessage
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class AiMentorFragment : Fragment() {

    private var _binding: FragmentAiMentorBinding? = null
    private val binding get() = _binding!!

    private val messages = mutableListOf<ChatMessage>()
    private lateinit var adapter: ChatAdapter
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiMentorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChatAdapter(messages)
        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
        }
        binding.rvMessages.adapter = adapter

        // Welcome message
        addMessage(ChatMessage(
            "Bonjour ! Je suis votre mentor PHP de SDS LABS. Posez-moi n'importe quelle question sur PHP : syntaxe, concepts, bugs, bonnes pratiques, ou demandez des exercices personnalisés. Je suis là pour vous aider à progresser rapidement ! 🚀",
            isAi = true
        ))

        binding.btnSend.setOnClickListener { sendMessage() }

        binding.etUserInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage()
                true
            } else false
        }
    }

    private fun sendMessage() {
        val text = binding.etUserInput.text.toString().trim()
        if (text.isEmpty()) return

        addMessage(ChatMessage(text, isAi = false))
        binding.etUserInput.setText("")

        // Show typing indicator
        val typingMsg = ChatMessage("...", isAi = true, isTyping = true)
        addMessage(typingMsg)

        scope.launch {
            val reply = withContext(Dispatchers.IO) { callClaude(text) }

            // Remove typing indicator
            messages.remove(typingMsg)
            adapter.notifyItemRemoved(messages.size)

            addMessage(ChatMessage(reply, isAi = true))

            val xpManager = (requireActivity() as MainActivity).xpManager
            xpManager.addXP(2)
            (requireActivity() as MainActivity).updateXPDisplay()
        }
    }

    private fun addMessage(message: ChatMessage) {
        messages.add(message)
        adapter.notifyItemInserted(messages.size - 1)
        binding.rvMessages.scrollToPosition(messages.size - 1)
    }

    private fun callClaude(userMessage: String): String {
        return try {
            val body = JSONObject().apply {
                put("model", "claude-sonnet-4-20250514")
                put("max_tokens", 1000)
                put("system", """Tu es un expert PHP et mentor pédagogique pour SDS LABS, une application d'apprentissage premium. 
Tu aides des apprenants débutants à intermédiaires à maîtriser PHP rapidement.
Réponds en français. Sois concis, pédagogique et pratique.
Utilise des exemples de code PHP avec balises ```php ... ``` quand c'est pertinent.
Encourage toujours l'apprenant. Max 3 paragraphes sauf pour du code.""")
                put("messages", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", userMessage)
                    })
                })
            }.toString()

            val request = Request.Builder()
                .url("https://api.anthropic.com/v1/messages")
                .post(body.toRequestBody("application/json".toMediaType()))
                .addHeader("Content-Type", "application/json")
                .addHeader("anthropic-version", "2023-06-01")
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: return "Aucune réponse reçue."

            if (!response.isSuccessful) {
                return "Erreur API (${response.code}). Vérifiez votre connexion internet."
            }

            val json = JSONObject(responseBody)
            val contentArray = json.getJSONArray("content")
            val result = StringBuilder()
            for (i in 0 until contentArray.length()) {
                val block = contentArray.getJSONObject(i)
                if (block.getString("type") == "text") {
                    result.append(block.getString("text"))
                }
            }
            result.toString().ifEmpty { "Désolé, aucune réponse générée." }

        } catch (e: Exception) {
            "Connexion impossible. Vérifiez votre connexion internet.\nDétail: ${e.message}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scope.cancel()
        _binding = null
    }
}
