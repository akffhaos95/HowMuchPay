package com.example.howmuch

import android.app.Application
import androidx.lifecycle.LiveData
import java.lang.Exception

class MenuRepository(application: Application) {
    private val menuDatabase = MenuDatabase.getInstance(application)!!
    private val menuDao : MenuDao = menuDatabase.menu_Dao()
    private val menu: LiveData<List<Menu>> = menuDao.getAll()

    fun getAll(): LiveData<List<Menu>> { return menu }
    fun getPrice(groupId:Int?): LiveData<String> {
        return menuDao.getPrice(groupId)
    }
    fun insert(menu: Menu){
        try {
            val thread = Thread(Runnable {
                menuDao.insert(menu) })
            thread.start()
        } catch (e: Exception) { }
    }
     fun deleteMenu(menu: Menu){
        try{
            val thread = Thread(Runnable {
                menuDao.deleteMenu(menu) })
        thread.start()
        } catch (e: Exception) { }
    }
}