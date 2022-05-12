package edu.mike.frontend.taskapp.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import edu.mike.frontend.taskapp.databinding.ActivityMainBinding
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    // Definition of the binding variable
    private lateinit var binding : ActivityMainBinding

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // With View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Old way without View Binding
        //setContentView(R.layout.activity_main)

        // Observer method to bind data into text views
        taskViewModel.task.observe(this) {
            binding.tvTitle.text = it.title
            binding.tvNotes.text = it.notes
        }

        // Listener when the user click the screen container
        binding.viewContainer.setOnClickListener {
            taskViewModel.getTask()
        }
    }
}