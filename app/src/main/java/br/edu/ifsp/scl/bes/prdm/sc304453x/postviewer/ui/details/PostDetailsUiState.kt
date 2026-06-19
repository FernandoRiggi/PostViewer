package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.details

import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.domain.model.Comment

data class PostDetailsUiState(
    val isLoading: Boolean = false,
    val comments: List<Comment> = emptyList(),
    val newCommentText: String = "",
    val errorMessage: String? = null
)