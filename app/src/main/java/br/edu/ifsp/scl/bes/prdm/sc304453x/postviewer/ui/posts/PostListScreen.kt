package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.posts

import androidx.compose.foundation.clickable
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
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.domain.model.Post
@Composable
fun PostListScreen(
    uiState: PostListUiState,
    onPostClick: (Post) -> Unit,
    onRetryClick: () -> Unit
) {
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

        uiState.posts.isEmpty() -> {
            EmptyContent(message = "Nenhum post encontrado.")
        }

        else -> {
            PostListContent(
                posts = uiState.posts,
                onPostClick = onPostClick
            )
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
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
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
private fun PostListContent(
    posts: List<Post>,
    onPostClick: (Post) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(posts) { post ->
            PostItem(
                post = post,
                onClick = { onPostClick(post) }
            )
        }
    }
}

@Composable
private fun PostItem(
    post: Post,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostListScreenSuccessPreview() {
    PostListScreen(
        uiState = PostListUiState(
            posts = listOf(
                Post(
                    id = 1,
                    title = "Título do primeiro post",
                    body = "Esse é o corpo do primeiro post exibido no preview."
                ),
                Post(
                    id = 2,
                    title = "Título do segundo post",
                    body = "Esse é o corpo do segundo post exibido no preview."
                )
            )
        ),
        onPostClick = {},
        onRetryClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PostListScreenLoadingPreview() {
    PostListScreen(
        uiState = PostListUiState(
            isLoading = true
        ),
        onPostClick = {},
        onRetryClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PostListScreenErrorPreview() {
    PostListScreen(
        uiState = PostListUiState(
            errorMessage = "Não foi possível carregar os posts."
        ),
        onPostClick = {},
        onRetryClick = {}
    )
}