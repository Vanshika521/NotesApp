package com.android_development.todoapp

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(
    private val viewModel: TaskViewModel
) : RecyclerView.Adapter<Adapter.TaskViewHolder>() {

    private var tasks: List<TaskEntity> = emptyList()

    fun submitList(newList: List<TaskEntity>) {
        tasks = newList
        notifyDataSetChanged()
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTask: TextView = itemView.findViewById(R.id.txtTask)
        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.txtTask.text = task.title

        // EDIT
        holder.btnEdit.setOnClickListener {
            val editText = android.widget.EditText(holder.itemView.context)
            editText.setText(task.title)

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Edit Task")
                .setView(editText)
                .setPositiveButton("Save") { _, _ ->
                    viewModel.edit(task, editText.text.toString())
                }
                .setNegativeButton("Cancel", null)
                .show()
        }


        holder.btnDelete.setOnClickListener {
            showDeleteConfirmation(holder, task)
        }

        holder.itemView.setOnLongClickListener {
            showDeleteConfirmation(holder, task)
            true
        }
    }

    private fun showDeleteConfirmation(holder: TaskViewHolder, task: TaskEntity) {
        AlertDialog.Builder(holder.itemView.context)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Yes") { _, _ -> viewModel.dlt(task) }
            .setNegativeButton("No", null)
            .show()
    }
}
