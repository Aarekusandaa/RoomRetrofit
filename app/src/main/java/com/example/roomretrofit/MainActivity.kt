package com.example.roomretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.roomretrofit.UserRetrofit as UserRetrofit1


class MainActivity : AppCompatActivity() {

    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView1 = findViewById(R.id.textView)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)

        val gson = Gson()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "users_db"
        ).build()

        val userDao = db.userDao()

        val service: UserRetrofit1 = retrofit.create(UserRetrofit1::class.java)
        val call = service.getUsers()

        call.enqueue(object: Callback<List<UsersTable>>{
            override fun onResponse(call: Call<List<UsersTable>>?, response: Response<List<UsersTable>>) {
                if (response.code() == 200) {
                    db.userDao().pushUser(response.body()!!)
                }
            }
            override fun onFailure(call: Call<List<UsersTable>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Retro_root_ide_package_.com.example.roomretrofit.UsersTable", Toast.LENGTH_SHORT).show()
            }

        })


        val user1: UsersTable = userDao.getUser(1)
        val user2: UsersTable = userDao.getUser(2)
        val user3: UsersTable = userDao.getUser(3)

        textView1.setText("id: " + user1.userId + " title: " + user1.title + " completed: " + user1.completed)
        textView2.setText("id: " + user2.userId + " title: " + user2.title + " completed: " + user2.completed)
        textView3.setText("id: " + user3.userId + " title: " + user3.title + " completed: " + user3.completed)
    }
}