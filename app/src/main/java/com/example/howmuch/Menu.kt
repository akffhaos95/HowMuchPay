package com.example.howmuch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu")
class Menu(@PrimaryKey(autoGenerate = true) var id: Int?,
                     @ColumnInfo(name = "name") var name: String?,
                     @ColumnInfo(name = "price") var price: String?,
                     @ColumnInfo(name = "groupId") var groupId: Int?
) {
    constructor(): this(null,"","",0)
}