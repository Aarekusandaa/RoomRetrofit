package com.example.roomretrofit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM Users WHERE id = :id")
    suspend fun getUser(id: Int): UsersTable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun pushUser(usersList: List<UsersTable>)
}

