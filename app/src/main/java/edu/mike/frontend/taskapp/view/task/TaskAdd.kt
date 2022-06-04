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
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel
import edu.mike.frontend.taskapp.viewmodel.TaskViewModelFactory

class TaskAdd : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentTaskAddBinding? = null
    private val binding get() = _binding!!

    // View model
    private lateinit var taskViewModel: TaskViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var priorities = ArrayList<Priority>()

        priorities.add(Priority(id = 1, label = "Height"))
        priorities.add(Priority(id = 2, label = "Medium"))
        priorities.add(Priority(id = 3, label = "Low"))

        _binding = FragmentTaskAddBinding.inflate(inflater, container, false)

        // TaskViewModelFactory
        taskViewModel =
            ViewModelProvider(this, TaskViewModelFactory())[TaskViewModel::class.java]

        // access the spinner
        if (binding.spinnerPriority != null) {
            val adapter : ArrayAdapter<Priority> = ArrayAdapter<Priority>(
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
                    val prioritySelected : Priority = parent.selectedItem as Priority
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}