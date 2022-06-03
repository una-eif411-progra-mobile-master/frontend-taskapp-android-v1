package edu.mike.frontend.taskapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import edu.mike.frontend.taskapp.adapter.TaskAdapter
import edu.mike.frontend.taskapp.databinding.ActivityTaskBinding
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel
import edu.mike.frontend.taskapp.viewmodel.TaskViewModelFactory

class TaskActivity : AppCompatActivity() {

    // Definition of the binding variable
    private lateinit var binding: ActivityTaskBinding
    private lateinit var taskViewModel: TaskViewModel

    // We need an adapter to connect with the recycle view
    private val adapter = TaskAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // With View Binding
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Connect the recycler view with the adapter
        binding.rvTaskList.layoutManager = LinearLayoutManager(this)
        binding.rvTaskList.adapter = adapter

        // TaskViewModelFactory
        taskViewModel =
            ViewModelProvider(this, TaskViewModelFactory())[TaskViewModel::class.java]

        // Observer method to bind data of task into text views
        taskViewModel.task.observe(this) {
            binding.tvTitle.text = it.title
            binding.tvNotes.text = it.notes
        }

        // Listener when the user click the screen container
        binding.viewContainer.setOnClickListener {
            taskViewModel.getTask(1)
        }

        // Observer method to bind data of taskList into Recycler View
        taskViewModel.taskList.observe(this) {
            adapter.setTaskList(it)
        }

        // We need when the Activity is created
        taskViewModel.findAllTask()
    }
}