package com.ranielschneider.whatsappclone.data

data class Conversation(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int
)
