package com.example.howmuch

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "menu")
class Menu constructor(@PrimaryKey(autoGenerate = true) var id: Int?,
                               @ColumnInfo(name = "name") var name: String?,
                               @ColumnInfo(name = "price") var price: String?,
                               @ColumnInfo(name = "groupId") var groupId: Int?
) {
    constructor(): this(null,"","",0)
}