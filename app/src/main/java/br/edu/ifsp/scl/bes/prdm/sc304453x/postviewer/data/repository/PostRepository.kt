package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.repository

import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.local.LocalCommentDao
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.local.LocalCommentEntity
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.remote.PostApi
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.domain.model.Comment
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.domain.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepository(
    private val postApi: PostApi,
    private val localCommentDao: LocalCommentDao
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

    fun observeLocalCommentsByPostId(postId: Int): Flow<List<Comment>> {
        return localCommentDao.observeCommentsByPostId(postId).map { localComments ->
            localComments.map { localComment ->
                Comment(
                    id = localComment.id,
                    postId = localComment.postId,
                    name = localComment.name,
                    email = localComment.email,
                    body = localComment.body,
                    isLocal = true
                )
            }
        }
    }

    suspend fun addLocalComment(
        postId: Int,
        body: String
    ) {
        localCommentDao.insertComment(
            LocalCommentEntity(
                postId = postId,
                name = "Comentário local",
                email = "local@postviewer.com",
                body = body
            )
        )
    }
}