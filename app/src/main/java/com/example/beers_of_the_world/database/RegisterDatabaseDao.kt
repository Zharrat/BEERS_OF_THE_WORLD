package com.example.beers_of_the_world.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface RegisterDatabaseDao {

    @Insert
    fun insert(register: RegisterEntity)

    //@Delete
    //suspend  fun deleteSubscriber(register: RegisterEntity):Int


    @Query("SELECT * FROM Register_users_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<RegisterEntity>>

    @Query("DELETE FROM Register_users_table")
    fun deleteAll(): Int

    @Query("SELECT * FROM Register_users_table WHERE user_name LIKE :userName")
    fun getUsername(userName: String): RegisterEntity?

}