package edu.mike.frontend.taskapp.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.mike.frontend.taskapp.adapter.TaskAdapter
import edu.mike.frontend.taskapp.databinding.ActivityMainBinding
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    // Definition of the binding variable
    private lateinit var binding : ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels()

    // We need an adapter to connect with the recycle view
    private val adapter = TaskAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // With View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Connect the recycler view with the adapter
        binding.rvTaskList.layoutManager = LinearLayoutManager(this)
        binding.rvTaskList.adapter = adapter

        // Old way without View Binding
        //setContentView(R.layout.activity_main)

        // Observer method to bind data of task into text views
        taskViewModel.task.observe(this) {
            binding.tvTitle.text = it.title
            binding.tvNotes.text = it.notes
        }

        // Listener when the user click the screen container
        binding.viewContainer.setOnClickListener {
            taskViewModel.getTask()
        }

        // Observer method to bind data of taskList into Recycler View
        taskViewModel.taskList.observe(this) {
            adapter.setTaskList(it)
        }

        // We need when the Activity is created
        taskViewModel.findAllTask()
    }
}