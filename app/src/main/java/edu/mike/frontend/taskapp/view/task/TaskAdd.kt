package edu.mike.frontend.taskapp.view.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import edu.mike.frontend.taskapp.R
import edu.mike.frontend.taskapp.databinding.FragmentTaskAddBinding
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel
import edu.mike.frontend.taskapp.viewmodel.TaskViewModelFactory

class TaskAdd : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentTaskAddBinding ?= null
    private val binding get() = _binding!!

    // View model
    private lateinit var taskViewModel: TaskViewModel

    private val languages = arrayOf("High", "Medium", "Low")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskAddBinding.inflate(inflater, container, false)

        // TaskViewModelFactory
        taskViewModel =
            ViewModelProvider(this, TaskViewModelFactory())[TaskViewModel::class.java]

        // access the spinner
        if (binding.spinnerPriority != null) {
            val adapter = ArrayAdapter(activity?.applicationContext!!, android.R.layout.simple_spinner_item, languages)

            binding.spinnerPriority.adapter = adapter

            binding.spinnerPriority.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

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