package com.example.howmuch

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Menu::class], version = 2)
abstract class MenuDatabase: RoomDatabase() {
    abstract fun menu_Dao(): MenuDao

    companion object {
        private var INSTANCE: MenuDatabase? = null
        fun getInstance(context: Context): MenuDatabase? {
            if (INSTANCE == null){
                synchronized(MenuDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MenuDatabase::class.java, "menu.db")
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