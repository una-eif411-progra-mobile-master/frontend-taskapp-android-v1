package edu.mike.frontend.taskapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.mike.frontend.taskapp.databinding.TaskItemBinding
import edu.mike.frontend.taskapp.model.Task

class TaskAdapter : RecyclerView.Adapter<MainViewHolder>(){

    private var tasks = mutableListOf<Task>()

    fun setTaskList(tasks: List<Task>) {
        this.tasks = tasks.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(inflater, parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val task = tasks[position]
        holder.binding.title.text = task.title
        holder.binding.notes.text = task.notes
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}

class MainViewHolder(
    val binding: TaskItemBinding
) : RecyclerView.ViewHolder(binding.root)