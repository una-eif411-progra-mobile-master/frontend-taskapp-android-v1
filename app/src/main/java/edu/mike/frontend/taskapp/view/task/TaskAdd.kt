package edu.mike.frontend.taskapp.view.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.mike.frontend.taskapp.databinding.FragmentTaskAddBinding
import edu.mike.frontend.taskapp.model.Priority
import edu.mike.frontend.taskapp.viewmodel.PriorityViewModel
import edu.mike.frontend.taskapp.viewmodel.PriorityViewModelFactory
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel
import edu.mike.frontend.taskapp.viewmodel.TaskViewModelFactory

class TaskAdd : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentTaskAddBinding? = null
    private val binding get() = _binding!!

    // View model
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var priorityViewModel: PriorityViewModel

    private lateinit var priorities : List<Priority>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTaskAddBinding.inflate(inflater, container, false)

        // TaskViewModelFactory
        taskViewModel =
            ViewModelProvider(this, TaskViewModelFactory())[TaskViewModel::class.java]

        priorityViewModel =
            ViewModelProvider(this, PriorityViewModelFactory())[PriorityViewModel::class.java]

        // Observer method to bind data
        priorityViewModel.priorityList.observe(viewLifecycleOwner) {
            priorities = it
            // access the spinner
            if (binding.spinnerPriority != null && priorities != null) {
                val adapter: ArrayAdapter<Priority> = ArrayAdapter<Priority>(
                    activity?.applicationContext!!,
                    android.R.layout.simple_spinner_item,
                    priorities
                )

                binding.spinnerPriority.adapter = adapter

                binding.spinnerPriority.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View, position: Int, id: Long
                    ) {
                        val prioritySelected: Priority = parent.selectedItem as Priority
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }
            }
        }

        priorityViewModel.findAllPriorities()

        // Inflate the layout for this fragment
        return binding.root
    }
}