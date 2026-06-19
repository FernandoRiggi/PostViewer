package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.domain.model

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val commentsCount: Int = 0
)