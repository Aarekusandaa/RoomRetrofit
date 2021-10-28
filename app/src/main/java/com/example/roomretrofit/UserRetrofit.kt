package com.example.roomretrofit

import retrofit2.Response
import retrofit2.http.GET

interface UserRetrofit {
    @GET("users/1/todos")
    suspend fun getUsers(): Response<List<UsersTable>>
}