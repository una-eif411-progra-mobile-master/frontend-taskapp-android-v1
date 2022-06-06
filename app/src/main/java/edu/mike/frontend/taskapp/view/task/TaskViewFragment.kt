package edu.mike.frontend.taskapp.view.task

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import edu.mike.frontend.taskapp.R
import edu.mike.frontend.taskapp.adapter.TaskAdapter.Companion.TASK_ID
import edu.mike.frontend.taskapp.databinding.FragmentTaskViewBinding
import edu.mike.frontend.taskapp.viewmodel.StateTask
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel

class TaskViewFragment : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentTaskViewBinding? = null
    private val binding get() = _binding!!

    // Shared view model
    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTaskViewBinding.inflate(inflater, container, false)

        val taskId: String = arguments?.getString(TASK_ID) ?: "0"

        // Observer method to bind data of task into text views
        taskViewModel.state.observe(viewLifecycleOwner) { state ->
            // this lets us avoid repeating 'binding.frameNews' before everything
            with(binding.root) {
                when (state) {
                    // just checking equality because Loading is a -singleton object instance-
                    StateTask.Loading -> {
                        // TODO: If you need do something in loading
                    }
                    // Error and Success are both -classes- so we need to check their type with 'is'
                    is StateTask.Error -> {
                        // TODO: If you need do something in error
                    }
                    is StateTask.Success -> {
                        state.task?.let {
                            binding.txtTaskTitle.text = it.title
                            binding.txtNotes.text = it.notes
                        }
                    }
                    else -> {
                        // TODO: Not state loaded
                    }
                }
            }
        }

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
            val bundle = bundleOf(TASK_ID to taskId)

            findNavController().navigate(R.id.action_taskScreen_to_taskUpdate, bundle)
        }

        taskViewModel.getTask(taskId.toLong())

        // Inflate the layout for this fragment
        return binding.root
    }


}