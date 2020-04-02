package com.example.howmuch

import androidx.room.*

@Entity(tableName = "menu")
class Menu constructor(@PrimaryKey(autoGenerate = true) var id: Int?,
                               @ColumnInfo(name = "name") var name: String?,
                               @ColumnInfo(name = "price") var price: String?,
                               @ColumnInfo(name = "groupId") var groupId: Int?
) { constructor(): this(null,"","",0) }

@Entity(tableName = "group")
class Group constructor(@PrimaryKey(autoGenerate = true) var id: Int?,
                        @ColumnInfo(name = "name") var name: String?
){ constructor(): this(null,"") }

@Entity(tableName = "member")
class Member constructor(@PrimaryKey(autoGenerate = true) var id: Int?,
                         @ColumnInfo(name = "name") var name: String?,
                         @ColumnInfo(name = "groupId") var groupId: Int?
){ constructor(): this(null,"",0) }

@Entity(primaryKeys = ["menuId", "memberId"])
data class Test(
    @ColumnInfo(name = "memberId") val memberId: Int?,
    @ColumnInfo(name = "menuId") val menuId: Int?
)