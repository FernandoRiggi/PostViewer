package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.repository.PostRepository
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.domain.model.Comment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    private val postId: Int,
    private val repository: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private var apiComments: List<Comment> = emptyList()
    private var localComments: List<Comment> = emptyList()

    init {
        loadComments()
        observeLocalComments()
    }

    fun loadComments() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                apiComments = repository.getCommentsByPostId(postId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        comments = apiComments + localComments
                    )
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Não foi possível carregar os comentários."
                    )
                }
            }
        }
    }

    private fun observeLocalComments() {
        viewModelScope.launch {
            repository.observeLocalCommentsByPostId(postId).collect { comments ->
                localComments = comments

                _uiState.update {
                    it.copy(
                        comments = apiComments + localComments
                    )
                }
            }
        }
    }

    fun onCommentTextChange(text: String) {
        _uiState.update {
            it.copy(newCommentText = text)
        }
    }

    fun addLocalComment() {
        val commentText = _uiState.value.newCommentText.trim()

        if (commentText.isBlank()) return

        viewModelScope.launch {
            repository.addLocalComment(
                postId = postId,
                body = commentText
            )

            _uiState.update {
                it.copy(newCommentText = "")
            }
        }
    }
}