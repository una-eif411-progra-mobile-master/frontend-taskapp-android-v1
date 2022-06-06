package edu.mike.frontend.taskapp.view.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.mike.frontend.taskapp.adapter.TaskAdapter
import edu.mike.frontend.taskapp.databinding.FragmentTaskListBinding
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel
import edu.mike.frontend.taskapp.viewmodel.TaskViewModelFactory

class TaskListFragment : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentTaskListBinding?=null
    private val binding get() = _binding!!

    // View model
    /**
     * by activityViewModels is a property delegate to get a reference to the ViewModel scoped to
     * its Activity. So, our shared ViewModel will survive across Fragment recreation.
     */
    private val taskViewModel: TaskViewModel by activityViewModels() {
        TaskViewModelFactory()
    }

    // We need an adapter to connect with the recycle view
    private val adapter = TaskAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)

        binding.rvfTaskList.adapter = adapter

        // Get all task from webservice
        taskViewModel.findAllTask()

        // Observer method to bind data of taskList into Recycler View
        taskViewModel.taskResponseList.observe(viewLifecycleOwner) {
            adapter.setTaskList(it)
        }

        return binding.root
    }
}