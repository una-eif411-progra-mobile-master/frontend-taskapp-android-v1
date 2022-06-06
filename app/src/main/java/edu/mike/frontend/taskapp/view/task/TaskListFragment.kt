package edu.mike.frontend.taskapp.view.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.mike.frontend.taskapp.adapter.TaskAdapter
import edu.mike.frontend.taskapp.databinding.FragmentTaskListBinding
import edu.mike.frontend.taskapp.viewmodel.StateTask
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel
import edu.mike.frontend.taskapp.viewmodel.TaskViewModelFactory

class TaskListFragment : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    // View model
    /**
     * By activityViewModels is a property delegate to get a reference to the ViewModel scoped to
     * its Activity. So, our shared ViewModel will survive across Fragment recreation.
     */
    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = TaskAdapter()

        _binding = FragmentTaskListBinding.inflate(inflater, container, false)

        binding.rvfTaskList.adapter = adapter

        // Get all task from webservice
        taskViewModel.findAllTask()

        taskViewModel.state.observe(viewLifecycleOwner) { state ->

            when (state) {
                // just checking equality because Loading is a -singleton object instance-
                StateTask.Loading -> {
                    // TODO: If you need do something in loading
                }
                // Error and Success are both -classes- so we need to check their type with 'is'
                is StateTask.Error -> {
                    // TODO: If you need do something in error
                }
                is StateTask.SuccessList -> {
                    state.taskList?.let { adapter.setTaskList(it) }
                }
                else -> {
                    // TODO: Not state loaded
                }
            }

        }

        return binding.root
    }
}