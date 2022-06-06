package edu.mike.frontend.taskapp.view.task

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.mike.frontend.taskapp.R
import edu.mike.frontend.taskapp.adapter.TaskAdapter.Companion.TASK_ID
import edu.mike.frontend.taskapp.databinding.FragmentTaskViewBinding
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel
import edu.mike.frontend.taskapp.viewmodel.TaskViewModelFactory

class TaskView : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentTaskViewBinding ?= null
    private val binding get() = _binding!!

    // View model
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTaskViewBinding.inflate(inflater, container, false)

        val taskId : String = arguments?.getString(TASK_ID) ?: "0"

        // TaskViewModelFactory
        taskViewModel =
            ViewModelProvider(this, TaskViewModelFactory())[TaskViewModel::class.java]

        // Observer method to bind data of task into text views
        taskViewModel.taskResponse.observe(viewLifecycleOwner) {
            binding.txtTaskTitle.text = it.title
            binding.txtNotes.text = it.notes
        }

        taskViewModel.getTask(taskId.toLong())

        binding.btnDelete.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireActivity())
            dialogBuilder.setMessage("Are you sure?")
                // if the dialog is cancelable
                .setCancelable(true)
                .setPositiveButton("Ok") { dialog, _ ->
                    taskViewModel.deleteTaskById(taskId.toLong())
                    findNavController().navigate(R.id.taskListScreen)
                    dialog.dismiss()

                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()

                }
            val alert = dialogBuilder.create()
            alert.setTitle("Delete Task")
            alert.show()
        }

        binding.btnUpdate.setOnClickListener {
            val bundle = bundleOf (TASK_ID to taskId)

            findNavController().navigate(R.id.action_taskScreen_to_taskUpdate, bundle)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}