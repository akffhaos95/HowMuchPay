package com.example.howmuch

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy.REPLACE

interface db_menu_Dao {
    @Query("SELECT * FROM menu")
    fun getAll(): List<Menu>

    @Insert(onConflict = REPLACE)
    fun insert(menu: db_menu_Entity)

    @Query("DELETE FROM menu")
    fun deleteAll()
}