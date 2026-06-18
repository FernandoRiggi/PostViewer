package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostListViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = PostListUiState(isLoading = true)

            try {
                val posts = repository.getPosts()

                _uiState.value = PostListUiState(
                    isLoading = false,
                    posts = posts
                )
            } catch (exception: Exception) {
                _uiState.value = PostListUiState(
                    isLoading = false,
                    errorMessage = "Não foi possível carregar os posts."
                )
            }
        }
    }
}