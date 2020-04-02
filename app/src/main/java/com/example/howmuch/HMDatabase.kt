package com.example.howmuch

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Menu::class,Member::class), version = 2)
abstract class HMDatabase: RoomDatabase() {
    abstract fun hmDao(): HMDao

    companion object {
        private var INSTANCE: HMDatabase? = null
        fun getInstance(context: Context): HMDatabase? {
            if (INSTANCE == null){
                synchronized(HMDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        HMDatabase::class.java, "howmuch.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}