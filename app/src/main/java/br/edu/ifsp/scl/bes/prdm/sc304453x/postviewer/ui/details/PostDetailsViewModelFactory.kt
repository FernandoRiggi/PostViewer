package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.repository.PostRepository

class PostDetailsViewModelFactory(
    private val postId: Int,
    private val repository: PostRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostDetailsViewModel::class.java)) {
            return PostDetailsViewModel(
                postId = postId,
                repository = repository
            ) as T
        }

        throw IllegalArgumentException("ViewModel desconhecida: ${modelClass.name}")
    }
}