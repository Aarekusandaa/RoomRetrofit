package com.example.roomretrofit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "Users")
data class UsersTable (
    @PrimaryKey @ColumnInfo (name = "id") val userId: Int,
    @ColumnInfo (name = "title") val title: String,
    @ColumnInfo (name = "task") val completed: Boolean
)