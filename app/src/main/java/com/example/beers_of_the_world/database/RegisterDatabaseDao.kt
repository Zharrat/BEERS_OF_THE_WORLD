package com.example.beers_of_the_world.database


import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface RegisterDatabaseDao {


    //Querys to get data from the database.
    @Insert
    suspend fun insert(register: RegisterEntity)

    @Delete
    suspend fun deleteSubscriber(register: RegisterEntity):Int

    @Query("SELECT * FROM Register_users_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<RegisterEntity>>

    @Query("SELECT COUNT(user_name) FROM Register_users_table")
    fun countAllUsers():Int

    @Query("DELETE FROM Register_users_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM Register_users_table WHERE user_name LIKE :userName")
    suspend fun getUsername(userName: String): RegisterEntity?

}