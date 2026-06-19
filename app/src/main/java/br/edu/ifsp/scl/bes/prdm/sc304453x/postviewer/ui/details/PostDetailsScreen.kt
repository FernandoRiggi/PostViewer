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
                    body = "Esse é um comentário exibido no preview."
                ),
                Comment(
                    id = 2,
                    postId = 1,
                    name = "Outro comentário",
                    email = "outro@email.com",
                    body = "Mais um comentário para testar a lista."
                )
            )
        ),
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
        onRetryClick = {},
        onBackClick = {}
    )
}