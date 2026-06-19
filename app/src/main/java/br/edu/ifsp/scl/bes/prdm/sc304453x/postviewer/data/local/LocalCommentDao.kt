package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalCommentDao {

    @Query(
        """
        SELECT * 
        FROM local_comments 
        WHERE postId = :postId 
        ORDER BY createdAt DESC
        """
    )
    fun observeCommentsByPostId(postId: Int): Flow<List<LocalCommentEntity>>

    @Insert
    suspend fun insertComment(comment: LocalCommentEntity)
}