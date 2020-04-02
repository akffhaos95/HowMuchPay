package com.example.howmuch

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HMDao {
    @Query("SELECT * FROM menu")
    fun getAllMenu(): LiveData<List<Menu>>
    @Query("SELECT * FROM member")
    fun getAllMember(): LiveData<List<Member>>
    @Query("SELECT SUM(price) FROM menu WHERE groupId= :groupId")
    fun getPrice(groupId: Int?): LiveData<String>
    @Query("SELECT COUNT(*) FROM member WHERE groupId=:groupId")
    fun getMemberCnt(groupId: Int?): LiveData<Int>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMenu(menu: Menu)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(member: Member)
    @Query("DELETE FROM menu WHERE groupId=:groupId")
    fun deleteAll(groupId: Int?)
    @Delete
    fun deleteMenu(menu : Menu)
    @Delete
    fun deleteMember(member : Member)
}