package com.example.swipeassignment.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PendingProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pendingProductDao(): PendingProductDao

    companion object {
        const val DATABASE_NAME = "swipe_offline_db"
    }
}