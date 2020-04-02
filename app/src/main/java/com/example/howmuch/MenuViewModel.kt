package com.example.howmuch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MenuViewModel(application: Application) : AndroidViewModel(application) {
    private val menuDao: Db_menu_Dao
    private val executorService: ExecutorService

    val allMenu: LiveData<Db_menu_Entity>
        get() = menuDao.getAll()

    fun insertMenu(menu: Db_menu_Entity?) {
        executorService.execute({ menuDao.insert(menu) })
    }

    fun deleteMenu(id: Int?) {
        executorService.execute({ menuDao.deleteMenu(id) })
    }

    init {
        menuDao = Db_menu.getInstance(application)!!.menu_Dao()
        executorService = Executors.newSingleThreadExecutor()
    }
}