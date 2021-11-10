/*
 * Copyright (c) FS
 */



package com.example.beers_of_the_world.database

import android.util.Log

class RegisterRepository(private val dao: RegisterDatabaseDao) {

    val users = dao.getAllUsers()
    suspend fun insert(user: RegisterEntity) {
        return dao.insert(user)
    }

    suspend fun getUserName(userName: String): RegisterEntity?{
        Log.i("MYTAG", "inside Repository Getusers fun ")
        return dao.getUsername(userName)
    }

    suspend fun countAllUsers(): Int {
        return dao.countAllUsers()
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }

}