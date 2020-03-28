package com.example.howmuch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu")
class db_menu_Entity(@PrimaryKey(autoGenerate = true) var id: Int?,
                     @ColumnInfo(name = "name") var name: String?,
                     @ColumnInfo(name = "price") var price: String?
) {
    constructor(): this(null,"","")
}