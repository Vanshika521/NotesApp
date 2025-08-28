package com.android_development.todoapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks order by id desc")
    fun getAll(): LiveData<List<TaskEntity>>

    @Insert
    suspend fun insert(task: TaskEntity): Long

    @Update
    suspend fun update(task: TaskEntity): Int

    @Delete
    suspend fun delete(task: TaskEntity): Int
}
