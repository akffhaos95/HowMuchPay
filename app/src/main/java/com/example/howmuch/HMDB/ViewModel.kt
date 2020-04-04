package com.example.howmuch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)
    private val menu = repository.getAllMenu()
    private val group = repository.getAllGroup()

    //Group
    fun getAllGroup(): LiveData<List<Group>>{
        return this.group
    }
    fun insertGroup(group: Group){
        repository.insertGroup(group)
    }

    //Menu
    fun getAllMenu(): LiveData<List<Menu>> {
        return this.menu
    }
    fun getPrice(groupId:Int?): LiveData<String> {
        return repository.getPrice(groupId)
    }
    fun insertMenu(menu: Menu) {
        repository.insertMenu(menu)
    }
    fun deleteAll(groupId: Int?) {
        repository.deleteAll(groupId)
    }
    fun deleteMenu(menu: Menu) {
        repository.deleteMenu(menu)
    }

    //Member
    fun getAllMember(groupId: Int?): LiveData<List<Member>> {
        return repository.getAllMember(groupId)
    }
    fun getMemberCnt(groupId:Int?): LiveData<Int> {
        return repository.getMemberCnt(groupId)
    }
    fun insertMember(member: Member) {
        repository.insertMember(member)
    }
    fun deleteMember(member: Member) {
        repository.deleteMember(member)
    }
}