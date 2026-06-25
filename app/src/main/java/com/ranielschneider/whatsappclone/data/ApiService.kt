package com.ranielschneider.whatsappclone.data

import retrofit2.http.GET

interface ApiService {

    @GET(".")
    suspend fun getConversations(): ConversationResponse
}