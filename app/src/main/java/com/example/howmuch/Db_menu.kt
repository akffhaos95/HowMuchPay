package com.example.howmuch

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Db_menu_Entity::class], version = 2)
abstract class Db_menu: RoomDatabase() {
    abstract fun menu_Dao(): Db_menu_Dao

    companion object {
        private var INSTANCE: Db_menu? = null

        fun getInstance(context: Context): Db_menu? {
            if (INSTANCE == null){
                synchronized(Db_menu::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        Db_menu::class.java, "menu.db")
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