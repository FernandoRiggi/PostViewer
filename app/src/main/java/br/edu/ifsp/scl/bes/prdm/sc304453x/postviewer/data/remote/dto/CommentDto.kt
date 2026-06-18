package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.remote.dto

data class CommentDto(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
