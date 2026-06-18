package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.remote

import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.remote.dto.CommentDto
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.remote.dto.PostDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    @GET("posts/{id}/comments")
    suspend fun getCommentsByPostId(
        @Path("id") postId: Int
    ): List<CommentDto>
}