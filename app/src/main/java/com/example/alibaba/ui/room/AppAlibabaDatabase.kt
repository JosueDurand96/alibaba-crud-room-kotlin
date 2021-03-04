package com.example.alibaba.ui.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Alibaba::class], version = 1)
abstract class AppAlibabaDatabase: RoomDatabase() {

    abstract fun alibabaDao(): AlibabaDao

    companion object {
        private var INSTANCE: AppAlibabaDatabase? = null

        fun getInstance(context: Context): AppAlibabaDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppAlibabaDatabase::class.java, "database-alibaba"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }

    }

}