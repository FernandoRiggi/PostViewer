package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.domain.model

data class Comment (
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String,
    val isLocal: Boolean = false
)