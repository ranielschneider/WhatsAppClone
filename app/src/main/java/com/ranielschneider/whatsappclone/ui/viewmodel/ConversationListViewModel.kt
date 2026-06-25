package com.ranielschneider.whatsappclone.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranielschneider.whatsappclone.data.Conversation
import com.ranielschneider.whatsappclone.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ConversationListState {
    object Loading : ConversationListState()
    data class Success(val conversations: List<Conversation>) : ConversationListState()
    data class Error(val message: String) : ConversationListState()
}

class ConversationListViewModel : ViewModel() {

    private val _state = MutableStateFlow<ConversationListState>(ConversationListState.Loading)
    val state: StateFlow<ConversationListState> = _state

    init {
        fetchConversations()
    }

    private fun fetchConversations() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getConversations()
                _state.value = ConversationListState.Success(response.result)
            } catch (e: Exception) {
                _state.value = ConversationListState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}