package com.example.howmuch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)
    private val menu = repository.getAllMenu()
    private val member = repository.getAllMember()

    fun getAllMenu(): LiveData<List<Menu>> {
        return this.menu
    }
    fun getAllMember(): LiveData<List<Member>> {
        return this.member
    }
    fun getPrice(groupId:Int?): LiveData<String> {
        return repository.getPrice(groupId)

    }
    fun getMemberCnt(groupId:Int?): LiveData<Int> {
        return repository.getMemberCnt(groupId)
    }
    fun insertMenu(menu: Menu) {
        repository.insertMenu(menu)
    }
    fun insertMember(member: Member) {
        repository.insertMember(member)
    }
    fun deleteAll(groupId: Int?) {
        repository.deleteAll(groupId)
    }
    fun deleteMenu(menu: Menu) {
        repository.deleteMenu(menu)
    }
    fun deleteMember(member: Member) {
        repository.deleteMember(member)
    }
}