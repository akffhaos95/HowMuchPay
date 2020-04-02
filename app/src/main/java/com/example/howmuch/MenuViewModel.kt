package com.example.howmuch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MenuViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MenuRepository(application)
    private val menu = repository.getAll()

    fun getAll(): LiveData<List<Menu>> {
        return this.menu
    }
    fun getPrice(groupId:Int?): LiveData<String> {
        val price = repository.getPrice(groupId)
        return price
    }
    fun insert(menu: Menu) {
        repository.insert(menu)
    }
    fun deleteMenu(menu: Menu) {
        repository.deleteMenu(menu)
    }
}