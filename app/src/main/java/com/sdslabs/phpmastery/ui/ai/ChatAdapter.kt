package com.sdslabs.phpmastery.ui.ai

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sdslabs.phpmastery.R
import com.sdslabs.phpmastery.model.ChatMessage

class ChatAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    companion object {
        const val VIEW_AI = 0
        const val VIEW_USER = 1
    }

    override fun getItemViewType(position: Int) =
        if (messages[position].isAi) VIEW_AI else VIEW_USER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layout = if (viewType == VIEW_AI)
            R.layout.item_chat_ai
        else
            R.layout.item_chat_user
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: ChatMessage) {
            val tvMsg = itemView.findViewById<TextView>(R.id.tvMessageText)
            if (message.isTyping) {
                tvMsg.text = "● ● ●"
            } else {
                // Format code blocks
                val formatted = message.text
                    .replace(Regex("```php([\\s\\S]*?)```")) { match ->
                        "\n[CODE PHP]\n${match.groupValues[1].trim()}\n[/CODE]\n"
                    }
                    .replace(Regex("`([^`]+)`")) { match -> "[${match.groupValues[1]}]" }
                tvMsg.text = formatted
            }
        }
    }
}
