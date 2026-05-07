package com.sdslabs.phpmastery.model

data class ChatMessage(
    val text: String,
    val isAi: Boolean,
    val isTyping: Boolean = false
)
