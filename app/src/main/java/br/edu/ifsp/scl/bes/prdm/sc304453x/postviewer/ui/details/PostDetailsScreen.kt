package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.domain.model.Comment

@Composable
fun PostDetailsScreen(
    uiState: PostDetailsUiState,
    onCommentTextChange: (String) -> Unit,
    onAddCommentClick: () -> Unit,
    onRetryClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = onBackClick,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Voltar")
        }

        AddCommentForm(
            commentText = uiState.newCommentText,
            onCommentTextChange = onCommentTextChange,
            onAddCommentClick = onAddCommentClick
        )

        Spacer(modifier = Modifier.height(8.dp))

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

            else -> {
                CommentListContent(
                    comments = uiState.comments
                )
            }
        }
    }
}

@Composable
private fun AddCommentForm(
    commentText: String,
    onCommentTextChange: (String) -> Unit,
    onAddCommentClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = commentText,
            onValueChange = onCommentTextChange,
            label = {
                Text(text = "Novo comentário")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onAddCommentClick,
            enabled = commentText.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Adicionar comentário")
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
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onRetryClick) {
                Text(text = "Tentar novamente")
            }
        }
    }
}

@Composable
private fun CommentListContent(
    comments: List<Comment>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = comment.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = comment.email,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = comment.body,
                style = MaterialTheme.typography.bodyMedium
            )

            if (comment.isLocal) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Comentário local",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun PostDetailsScreenSuccessPreview() {
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