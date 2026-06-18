package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.remote.RetrofitClient
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.repository.PostRepository
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.navigation.AppNavigation
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.theme.PostViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = PostRepository(
            postApi = RetrofitClient.postApi
        )

        setContent {
            PostViewerTheme {
                AppNavigation(
                    repository = repository
                )
            }
        }
    }
}