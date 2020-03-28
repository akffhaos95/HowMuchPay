package com.example.howmuch

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Db_menu_Dao {
    @Query("SELECT * FROM menu")
    fun getNP(): List<Db_menu_Entity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg menu: Db_menu_Entity)

    @Query("DELETE FROM menu WHERE id = :id")
    fun deleteMenu(id : Int?)

    @Query("DELETE FROM menu")
    fun deleteAll()
}