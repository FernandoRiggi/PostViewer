package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.domain.model.Comment
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.theme.PostViewerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsScreen(
    uiState: PostDetailsUiState,
    onCommentTextChange: (String) -> Unit,
    onAddCommentClick: () -> Unit,
    onRetryClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Comentários",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            HeaderActions(
                onBackClick = onBackClick
            )

            AddCommentForm(
                commentText = uiState.newCommentText,
                onCommentTextChange = onCommentTextChange,
                onAddCommentClick = onAddCommentClick
            )

            when {
                uiState.isLoading -> {
                    LoadingContent()
                }

                uiState.errorMessage != null -> {
                    ErrorContent(
                        message = uiState.errorMessage,
                        onRetryClick = onRetryClick
                    )
                }

                uiState.comments.isEmpty() -> {
                    EmptyContent(message = "Nenhum comentário encontrado.")
                }

                else -> {
                    CommentListContent(
                        comments = uiState.comments
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderActions(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        OutlinedButton(
            onClick = onBackClick
        ) {
            Text(text = "Voltar")
        }
    }
}

@Composable
private fun AddCommentForm(
    commentText: String,
    onCommentTextChange: (String) -> Unit,
    onAddCommentClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Adicionar comentário local",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = commentText,
                onValueChange = onCommentTextChange,
                label = {
                    Text(text = "Digite seu comentário")
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onAddCommentClick,
                enabled = commentText.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Adicionar")
            }
        }
    }
}

@Composable
private fun CommentListContent(
    comments: List<Comment>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(comments) { comment ->
            CommentItem(comment = comment)
        }
    }
}

@Composable
private fun CommentItem(
    comment: Comment
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AssistChip(
                onClick = {},
                label = {
                    Text(
                        text = if (comment.isLocal) {
                            "Comentário local"
                        } else {
                            "Comentário da API"
                        }
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = comment.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = comment.email,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = comment.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onRetryClick) {
                Text(text = "Tentar novamente")
            }
        }
    }
}

@Composable
private fun EmptyContent(
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun PostDetailsScreenSuccessPreview() {
    PostViewerTheme {
        PostDetailsScreen(
            uiState = PostDetailsUiState(
                comments = listOf(
                    Comment(
                        id = 1,
                        postId = 1,
                        name = "Comentário de exemplo",
                        email = "usuario@email.com",
                        body = "Esse é um comentário vindo da API."
                    ),
                    Comment(
                        id = 2,
                        postId = 1,
                        name = "Comentário local",
                        email = "local@postviewer.com",
                        body = "Esse comentário foi salvo localmente no dispositivo.",
                        isLocal = true
                    )
                )
            ),
            onCommentTextChange = {},
            onAddCommentClick = {},
            onRetryClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PostDetailsScreenLoadingPreview() {
    PostDetailsScreen(
        uiState = PostDetailsUiState(
            isLoading = true
        ),
        onCommentTextChange = {},
        onAddCommentClick = {},
        onRetryClick = {},
        onBackClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PostDetailsScreenErrorPreview() {
    PostDetailsScreen(
        uiState = PostDetailsUiState(
            errorMessage = "Não foi possível carregar os comentários."
        ),
        onCommentTextChange = {},
        onAddCommentClick = {},
        onRetryClick = {},
        onBackClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PostDetailsScreenTypingPreview() {
    PostDetailsScreen(
        uiState = PostDetailsUiState(
            newCommentText = "Estou digitando um comentário...",
            comments = listOf(
                Comment(
                    id = 1,
                    postId = 1,
                    name = "Comentário de exemplo",
                    email = "usuario@email.com",
                    body = "Comentário já existente."
                )
            )
        ),
        onCommentTextChange = {},
        onAddCommentClick = {},
        onRetryClick = {},
        onBackClick = {}
    )
}