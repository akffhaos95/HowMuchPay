package com.example.howmuch

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "grouper")
class Group constructor(@PrimaryKey(autoGenerate = true) var id: Int?,
                        @ColumnInfo(name = "name") var name: String?
){ constructor(): this(null,"") }

@Entity(tableName = "menu", foreignKeys = arrayOf(
    ForeignKey(onDelete = CASCADE, entity = Group::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("groupId"))))
class Menu constructor(@PrimaryKey(autoGenerate = true) var id: Int?,
                               @ColumnInfo(name = "name") var name: String?,
                               @ColumnInfo(name = "price") var price: String?,
                               @ColumnInfo(name = "groupId") var groupId: Int?
) { constructor(): this(null,"","",0) }

@Entity(tableName = "member", foreignKeys = arrayOf(
    ForeignKey(onDelete = CASCADE, entity = Group::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("groupId"))))
class Member constructor(@PrimaryKey(autoGenerate = true) var id: Int?,
                         @ColumnInfo(name = "name") var name: String?,
                         @ColumnInfo(name = "groupId") var groupId: Int?
){ constructor(): this(null,"",0) }

@Entity(tableName = "payment", foreignKeys = arrayOf(
    ForeignKey(onDelete = CASCADE, entity = Group::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("groupId"))))
class Payment constructor(@PrimaryKey(autoGenerate = true) var id: Int?,
                          @ColumnInfo(name = "name") var name: String?,
                          @ColumnInfo(name = "payer") var payer: Int?,
                          @ColumnInfo(name = "giver") var giver: Int?,
                          @ColumnInfo(name = "price") var price: Int?,
                          @ColumnInfo(name = "groupId") var groupId: Int?
) { constructor(): this(null,"",0,0,0,0)}