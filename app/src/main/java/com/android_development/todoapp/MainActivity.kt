package com.android_development.todoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var adapter: Adapter
    private lateinit var editTask: EditText
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTask = findViewById(R.id.editTask)
        btnAdd = findViewById(R.id.btnAdd)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        adapter = Adapter(taskViewModel)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 1️⃣ Observe Room LiveData
        taskViewModel.all.observe(this, Observer { tasks ->
            adapter.submitList(tasks)
        })

        // 2️⃣ Fetch notes from API and save to Room
        taskViewModel.fetchNotes()  // <-- minimal addition

        // Add Task button
        btnAdd.setOnClickListener {
            val taskTitle = editTask.text.toString()
            if (taskTitle.isNotEmpty()) {
                taskViewModel.add(taskTitle, "") // empty desc for now
                editTask.text.clear()
            }
        }
    }
}
