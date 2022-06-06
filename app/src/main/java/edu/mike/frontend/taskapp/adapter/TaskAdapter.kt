package edu.mike.frontend.taskapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import edu.mike.frontend.taskapp.R
import edu.mike.frontend.taskapp.databinding.TaskItemBinding
import edu.mike.frontend.taskapp.model.TaskResponse

class TaskAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private var taskList = mutableListOf<TaskResponse>()

    fun setTaskList(taskResponseList: List<TaskResponse>) {
        this.taskList.clear()
        this.taskList.addAll(taskResponseList.toMutableList())
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(inflater, parent, false)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.title.text = task.title
        holder.binding.notes.text = task.notes

        holder.itemView.setOnClickListener() {
            val bundle = bundleOf(TASK_ID to taskList[position].id.toString())

            holder.itemView.findNavController().navigate(
                R.id.action_taskListScreen_to_taskScreen, bundle
            )
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}

class MainViewHolder(
    val binding: TaskItemBinding
) : RecyclerView.ViewHolder(binding.root)