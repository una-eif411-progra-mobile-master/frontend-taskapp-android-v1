package edu.mike.frontend.taskapp.view.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import edu.mike.frontend.taskapp.adapter.TaskAdapter
import edu.mike.frontend.taskapp.databinding.FragmentTaskListBinding
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel
import edu.mike.frontend.taskapp.viewmodel.TaskViewModelFactory

class TaskList : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentTaskListBinding?=null
    private val binding get() = _binding!!

    // View model
    private lateinit var taskViewModel: TaskViewModel

    // We need an adapter to connect with the recycle view
    private val adapter = TaskAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        binding.rvfTaskList.adapter = adapter

        // TaskViewModelFactory
        taskViewModel =
            ViewModelProvider(this, TaskViewModelFactory())[TaskViewModel::class.java]

        // Observer method to bind data of taskList into Recycler View
        taskViewModel.taskResponseList.observe(viewLifecycleOwner) {
            adapter.setTaskList(it)
        }

        // Get all task from webservice
        taskViewModel.findAllTask()

        return binding.root
    }
}