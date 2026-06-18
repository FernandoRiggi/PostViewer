package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.posts

import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.domain.model.Post

data class PostListUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val errorMessage: String? = null
)