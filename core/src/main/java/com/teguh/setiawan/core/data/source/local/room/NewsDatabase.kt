package com.teguh.setiawan.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.teguh.setiawan.core.data.source.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}