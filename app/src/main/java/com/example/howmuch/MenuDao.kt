package com.example.howmuch

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu")
    fun getAll(): LiveData<List<Menu>>

    @Query("SELECT * FROM menu WHERE groupId = :groupId")
    fun getGroupMenu(groupId : Int?): LiveData<List<Menu>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(menu: Menu)

    @Delete
    fun deleteAll(menu : Menu)

    @Delete
    fun deleteMenu(menu : Menu)
}