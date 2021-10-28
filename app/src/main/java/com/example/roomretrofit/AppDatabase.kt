package com.example.roomretrofit

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(UsersTable::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}