package com.example.howmuch

import android.app.Application
import androidx.lifecycle.LiveData
import java.lang.Exception

class Repository(application: Application) {
    private val hmDatabase = HMDatabase.getInstance(application)!!
    private val hmDao : HMDao = hmDatabase.hmDao()
    private val menu: LiveData<List<Menu>> = hmDao.getAllMenu()
    private val member: LiveData<List<Member>> = hmDao.getAllMember()

    fun getAllMenu(): LiveData<List<Menu>> { return menu }
    fun getAllMember(): LiveData<List<Member>> { return member }
    fun getPrice(groupId:Int?): LiveData<String> {
        return hmDao.getPrice(groupId)
    }
    fun getMemberCnt(groupId:Int?): LiveData<Int> {
        return hmDao.getMemberCnt(groupId)
    }
    fun insertMenu(menu: Menu){
        try {
            val thread = Thread(Runnable {
                hmDao.insertMenu(menu) })
            thread.start()
        } catch (e: Exception) { }
    }
    fun insertMember(member: Member){
        try {
            val thread = Thread(Runnable {
                hmDao.insertMember(member) })
            thread.start()
        } catch (e: Exception) { }
    }
    fun deleteAll(groupId: Int?){
        try{
            val thread = Thread(Runnable {
                hmDao.deleteAll(groupId) })
            thread.start()
        } catch (e: Exception) { }
    }
    fun deleteMenu(menu: Menu){
        try{
            val thread = Thread(Runnable {
                hmDao.deleteMenu(menu) })
        thread.start()
        } catch (e: Exception) { }
    }
    fun deleteMember(member: Member){
        try{
            val thread = Thread(Runnable {
                hmDao.deleteMember(member) })
            thread.start()
        } catch (e: Exception) { }
    }
}