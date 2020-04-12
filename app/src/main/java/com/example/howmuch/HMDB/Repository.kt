package com.example.howmuch

import android.app.Application
import androidx.lifecycle.LiveData
import java.lang.Exception

class Repository(application: Application) {
    private val hmDatabase = HMDatabase.getInstance(application)!!
    private val hmDao : HMDao = hmDatabase.hmDao()
    private val grouper: LiveData<List<Group>> = hmDao.getAllGroup()

    //Group
    //SELECT
    fun getAllGroup(): LiveData<List<Group>> { return grouper }
    fun insertGroup(group: Group){
        try {
            val thread = Thread(Runnable {
                hmDao.insertGroup(group) })
            thread.start()
        } catch (e: Exception) { }
    }
    fun deleteGroup(group: Group){
        try {
            val thread = Thread(Runnable {
                hmDao.deleteGroup(group) })
            thread.start()
        } catch (e: Exception) { }
    }

    //Menu
    //SELECT
    fun getAllMenu(groupId: Int?): LiveData<List<Menu>> {
        return hmDao.getAllMenu(groupId) }
    fun getPrice(groupId:Int?): LiveData<String> {
        return hmDao.getPrice(groupId)
    }
    //INSERT
    fun insertMenu(menu: Menu){
        try {
            val thread = Thread(Runnable {
                hmDao.insertMenu(menu) })
            thread.start()
        } catch (e: Exception) { }
    }
    //DELETE
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

    //Member
    //SELECT
    fun getAllMember(groupId: Int?): LiveData<List<Member>> {
        return hmDao.getAllMember(groupId)
    }
    fun getMemberCnt(groupId:Int?): LiveData<Int> {
        return hmDao.getMemberCnt(groupId)
    }
    //INSERT
    fun insertMember(member: Member){
        try {
            val thread = Thread(Runnable {
                hmDao.insertMember(member) })
            thread.start()
        } catch (e: Exception) { }
    }
    //DELETE
    fun deleteMember(member: Member){
        try{
            val thread = Thread(Runnable {
                hmDao.deleteMember(member) })
            thread.start()
        } catch (e: Exception) { }
    }

    //Payment
    //SELECT
    fun getPayment(groupId: Int?): LiveData<List<Payment>>{
        return hmDao.getPayment(groupId)
    }
    //INSERT
    fun insertPayment(payment: Payment){
        try{
            val thread = Thread(Runnable {
                hmDao.insertPayment(payment) })
            thread.start()
        } catch (e: Exception) { }
    }
}