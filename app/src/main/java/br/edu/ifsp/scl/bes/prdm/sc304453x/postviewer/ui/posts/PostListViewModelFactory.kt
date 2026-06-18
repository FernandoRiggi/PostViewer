package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.repository.PostRepository

class PostListViewModelFactory(
    private val repository: PostRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostListViewModel::class.java)) {
            return PostListViewModel(repository) as T
        }

        throw IllegalArgumentException("ViewModel desconhecida: ${modelClass.name}")
    }
}