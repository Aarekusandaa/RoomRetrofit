package com.example.roomretrofit

import retrofit2.Call
import retrofit2.http.GET

public interface UserRetrofit {

    @GET("users/1/todos")
    fun getUsers (): Call<List<UsersTable>>
}