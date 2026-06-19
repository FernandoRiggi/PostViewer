package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.repository.PostRepository
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.details.PostDetailsScreen
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.details.PostDetailsViewModel
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.details.PostDetailsViewModelFactory
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.posts.PostListScreen
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.posts.PostListViewModel
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.posts.PostListViewModelFactory

@Composable
fun AppNavigation(
    repository: PostRepository
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.PostList.route
    ) {
        composable(AppRoute.PostList.route) {
            val postListViewModel: PostListViewModel = viewModel(
                factory = PostListViewModelFactory(repository)
            )

            val uiState by postListViewModel.uiState.collectAsStateWithLifecycle()

            PostListScreen(
                uiState = uiState,
                onPostClick = { post ->
                    navController.navigate(AppRoute.PostDetails.createRoute(post.id))
                },
                onRetryClick = postListViewModel::loadPosts
            )
        }

        composable(
            route = AppRoute.PostDetails.route,
            arguments = listOf(
                navArgument(AppRoute.PostDetails.ARG_POST_ID) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val postId = backStackEntry.arguments
                ?.getInt(AppRoute.PostDetails.ARG_POST_ID)
                ?: return@composable

            val postDetailsViewModel: PostDetailsViewModel = viewModel(
                factory = PostDetailsViewModelFactory(
                    postId = postId,
                    repository = repository
                )
            )

            val uiState by postDetailsViewModel.uiState.collectAsStateWithLifecycle()

            PostDetailsScreen(
                uiState = uiState,
                onCommentTextChange = postDetailsViewModel::onCommentTextChange,
                onAddCommentClick = postDetailsViewModel::addLocalComment,
                onRetryClick = postDetailsViewModel::loadComments,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}