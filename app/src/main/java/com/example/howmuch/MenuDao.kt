package com.example.howmuch

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu")
    fun getAll(): LiveData<List<Menu>>
    @Query("SELECT SUM(price) FROM menu WHERE groupId=0")
    fun getPrice(): String
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(menu: Menu)

    @Delete
    fun deleteMenu(menu : Menu)
}