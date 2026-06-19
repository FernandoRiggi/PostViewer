package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    private val postId: Int,
    private val repository: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadComments()
    }

    fun loadComments() {
        viewModelScope.launch {
            _uiState.value = PostDetailsUiState(isLoading = true)

            try {
                val comments = repository.getCommentsByPostId(postId)

                _uiState.value = PostDetailsUiState(
                    isLoading = false,
                    comments = comments
                )
            } catch (exception: Exception) {
                _uiState.value = PostDetailsUiState(
                    isLoading = false,
                    errorMessage = "Não foi possível carregar os comentários."
                )
            }
        }
    }
}