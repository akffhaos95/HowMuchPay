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
    fun getPrice(): String {
        return repository.getPrice()
    }
    fun insert(menu: Menu) {
        repository.insert(menu)
    }
    fun deleteMenu(menu: Menu) {
        repository.deleteMenu(menu)
    }
}