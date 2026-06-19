package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.local.AppDatabase
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.remote.RetrofitClient
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.repository.PostRepository
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.navigation.AppNavigation
import br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.ui.theme.PostViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "postviewer.db"
        ).build()

        val repository = PostRepository(
            postApi = RetrofitClient.postApi,
            localCommentDao = database.localCommentDao()
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