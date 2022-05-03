package edu.mike.frontend.taskapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import edu.mike.frontend.taskapp.databinding.ActivityMainBinding
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    // Definition of the binding variable
    private lateinit var binding : ActivityMainBinding

    private val taskViewMode: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // With View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Old way without View Binding
        //setContentView(R.layout.activity_main)
    }
}