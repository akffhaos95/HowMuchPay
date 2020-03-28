package com.example.howmuch

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class db_menu: RoomDatabase() {
    abstract fun menu_Dao(): db_menu_Dao

    companion object {
        private var INSTANCE: db_menu? = null

        fun getInstance(context: Context): db_menu? {
            if (INSTANCE == null){
                synchronized(db_menu::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        db_menu::class.java, "menu.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}