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
    @Query("SELECT 1")
    suspend fun alive(): Int

    @Insert
    suspend fun insertUser(user: Users)

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): Users?

    @Query("SELECT COUNT(*) FROM users WHERE email = :email AND password = :password")
    suspend fun checkPassword(email: String, password: String): Int
}

fun getRoomDataBase(context: Context): RoomDataBase {
    return Room.databaseBuilder(
        context,
        RoomDataBase::class.java,
        "users.db"
    ).fallbackToDestructiveMigration().build()
}

@Database(entities = [Users::class], version = 1)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun UserDao(): UserDao
}

class UsersModel(private val room: RoomDataBase) : ViewModel() {
    val db = room.UserDao()

    init {
        viewModelScope.launch {
            db.alive()
        }
    }

    fun signUp(name: String, email: String, password: String) {
        val user = Users(name = name, email = email, password = password)
        viewModelScope.launch {
            db.insertUser(user)
        }
    }

    suspend fun signIn(email: String, password: String): Boolean {
        val count = db.checkPassword(email, password)
        return count > 0
    }
}