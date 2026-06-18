package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.repository

import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.remote.PostApi
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.domain.model.Comment
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.domain.model.Post

class PostRepository(
    private val postApi: PostApi
) {
    suspend fun getPosts(): List<Post> {
        return postApi.getPosts().map { postDto ->
            Post(
                id = postDto.id,
                title = postDto.title,
                body = postDto.body
            )
        }
    }

    suspend fun getCommentsByPostId(postId: Int): List<Comment> {
        return postApi.getCommentsByPostId(postId).map { commentDto ->
            Comment(
                id = commentDto.id,
                postId = commentDto.postId,
                name = commentDto.name,
                email = commentDto.email,
                body = commentDto.body,
                isLocal = false
            )
        }
    }
}