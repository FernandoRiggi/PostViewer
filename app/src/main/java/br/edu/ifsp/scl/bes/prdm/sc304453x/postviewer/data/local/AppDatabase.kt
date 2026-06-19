package br.edu.ifsp.scl.bes.prdm.sc304453x.postviewer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalCommentEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localCommentDao(): LocalCommentDao
}