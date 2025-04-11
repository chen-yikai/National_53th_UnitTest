package com.example.national53thunittest

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.launch

@Entity(tableName = "users")
data class Users(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val password: String
)

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: Users)

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): Users?

    @Query("SELECT COUNT(*) FROM users WHERE email = :email AND password = :password")
    suspend fun checkPassword(email: String, password: String): Int
}

@Database(entities = [Users::class], version = 1)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun UserDao(): UserDao
}

fun getRoomDataBase(context: Context): RoomDataBase {
    return Room
        .databaseBuilder(context.applicationContext, RoomDataBase::class.java, "db")
        .fallbackToDestructiveMigration()
        .build()
}

class UsersModel(private val db: RoomDataBase) : ViewModel() {
    fun signUp(name: String, email: String, password: String) {
        val user = Users(name = name, email = email, password = password)
        viewModelScope.launch {
            db.UserDao().insertUser(user)
        }
    }
}