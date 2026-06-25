package com.ranielschneider.whatsappclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ranielschneider.whatsappclone.data.Conversation
import com.ranielschneider.whatsappclone.ui.viewmodel.ConversationListState
import com.ranielschneider.whatsappclone.ui.viewmodel.ConversationListViewModel
import com.ranielschneider.whatsappclone.ui.theme.WhatsAppCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatsAppCloneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ConversationListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "WhatsApp Clone",
            modifier = Modifier.padding(16.dp)
        )
        HorizontalDivider()

        when (state) {
            is ConversationListState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
            is ConversationListState.Success -> {
                val conversations = (state as ConversationListState.Success).conversations
                ConversationList(conversations = conversations)
            }
            is ConversationListState.Error -> {
                val message = (state as ConversationListState.Error).message
                Text(text = "Erro: $message", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun ConversationList(conversations: List<Conversation>) {
    LazyColumn {
        items(conversations) { conversation ->
            ConversationItem(conversation = conversation)
            HorizontalDivider()
        }
    }
}

@Composable
fun ConversationItem(conversation: Conversation) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = conversation.avatarUrl,
            contentDescription = "Avatar de ${conversation.name}",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            Text(text = conversation.name)
            Text(
                text = conversation.lastMessage,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(text = conversation.timestamp)
    }
}

