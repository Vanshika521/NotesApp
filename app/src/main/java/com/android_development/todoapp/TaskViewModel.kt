package com.android_development.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepo
    val all: LiveData<List<TaskEntity>>

    init {
        val taskDao = TaskDb.getDatabase(application).taskDao()
        repository = TaskRepo(taskDao)
        all = repository.all
    }

    fun add(title: String, desc: String) = viewModelScope.launch {
        repository.insert(TaskEntity(title = title, desc = desc))
    }

    fun edit(task: TaskEntity, newTitle: String) = viewModelScope.launch {
        repository.update(task.copy(title = newTitle))
    }

    fun dlt(task: TaskEntity) = viewModelScope.launch {
        repository.delete(task)
    }
    fun fetchNotes() = viewModelScope.launch {
        repository.fetchFromApi()
    }

}
