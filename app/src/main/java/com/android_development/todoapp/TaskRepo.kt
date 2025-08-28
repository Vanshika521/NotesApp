package com.android_development.todoapp

import androidx.lifecycle.LiveData

class TaskRepo(private val taskDao: TaskDao) {

    val all: LiveData<List<TaskEntity>> = taskDao.getAll()

    suspend fun insert(task: TaskEntity) {
        taskDao.insert(task)
    }

    suspend fun update(task: TaskEntity) {
        taskDao.update(task)
    }

    suspend fun delete(task: TaskEntity) {
        taskDao.delete(task)
    }

    suspend fun fetchFromApi() {
        try {
            val notes = RetrofitInstance.api.getNotes()
            notes.forEach { note ->
                insert(TaskEntity(id = note.id, title = note.title, desc = note.body))
            }
        } catch (e: Exception) {
        }
    }

}
