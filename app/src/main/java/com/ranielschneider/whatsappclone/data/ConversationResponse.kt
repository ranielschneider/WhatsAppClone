package com.ranielschneider.whatsappclone.data

data class ConversationResponse(
    val status: String,
    val code: Int,
    val result: List<Conversation>
)