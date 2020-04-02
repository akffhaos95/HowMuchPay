package com.example.howmuch

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HMDao {
    //Group
    //SELECT
    @Query("SELECT * FROM grouper")
    fun getAllGroup(): LiveData<List<Group>>
    //INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroup(group: Group)

    //Menu
    //SELECT
    @Query("SELECT * FROM menu")
    fun getAllMenu(): LiveData<List<Menu>>
    @Query("SELECT SUM(price) FROM menu WHERE groupId= :groupId")
    fun getPrice(groupId: Int?): LiveData<String>
    //INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMenu(menu: Menu)
    //DELETE
    @Query("DELETE FROM menu WHERE groupId=:groupId")
    fun deleteAll(groupId: Int?)
    @Delete
    fun deleteMenu(menu : Menu)

    //Member
    //SELECT
    @Query("SELECT COUNT(*) FROM member WHERE groupId=:groupId")
    fun getMemberCnt(groupId: Int?): LiveData<Int>
    @Query("SELECT * FROM member WHERE groupId=:groupId")
    fun getAllMember(groupId: Int?): LiveData<List<Member>>
    //INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(member: Member)
    //DELETE
    @Delete
    fun deleteMember(member : Member)
}