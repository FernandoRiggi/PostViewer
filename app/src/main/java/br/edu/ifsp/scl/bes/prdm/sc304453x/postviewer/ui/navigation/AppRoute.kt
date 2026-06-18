package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.navigation

sealed class AppRoute(val route: String) {
    data object PostList : AppRoute("posts")

    data object PostDetails : AppRoute("posts/{postId}") {
        const val ARG_POST_ID = "postId"

        fun createRoute(postId: Int): String {
            return "posts/$postId"
        }
    }
}