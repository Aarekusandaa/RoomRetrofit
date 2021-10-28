package com.example.roomretrofit

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            this@MainActivity,
            AppDatabase::class.java, "users_db"
        ).build()

        val userDao = db.userDao()
        val service: UserRetrofit1 = retrofit.create(UserRetrofit1::class.java)
        val user1: MutableLiveData<UsersTable> = MutableLiveData()
        val user2: MutableLiveData<UsersTable> = MutableLiveData()
        val user3: MutableLiveData<UsersTable> = MutableLiveData()

        user1.observe(this, { usersTable ->
            usersTable?.let { nonNullUsersTable ->
                textView1.text = "id: " + nonNullUsersTable.userId + " title: " + nonNullUsersTable.title + " completed: " + nonNullUsersTable.completed
            }
        })
        user2.observe(this, { usersTable ->
            usersTable?.let { nonNullUsersTable ->
                textView2.text = "id: " + nonNullUsersTable.userId + " title: " + nonNullUsersTable.title + " completed: " + nonNullUsersTable.completed
            }
        })
        user3.observe(this, { usersTable ->
            usersTable?.let { nonNullUsersTable ->
                textView3.text = "id: " + nonNullUsersTable.userId + " title: " + nonNullUsersTable.title + " completed: " + nonNullUsersTable.completed
            }
        })
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (cacheUserTable(service, userDao)) {
                    withContext(Dispatchers.Main) {
                        user1.value = userDao.getUser(1)
                        user2.value = userDao.getUser(2)
                        user3.value = userDao.getUser(3)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MainActivity,
                            "Retro_root_ide_package_.com.example.roomretrofit.UsersTable",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    suspend fun cacheUserTable(service: UserRetrofit1, dao: UserDao): Boolean {
        val retrofitResponse = service.getUsers()
        if (retrofitResponse.isSuccessful) {
            retrofitResponse.body()?.let { data ->
                dao.pushUser(data)
            }
            return true
        }
        return false
    }
}