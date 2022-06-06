package edu.mike.frontend.taskapp.view.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import edu.mike.frontend.taskapp.BuildConfig
import edu.mike.frontend.taskapp.R
import edu.mike.frontend.taskapp.adapter.TaskAdapter
import edu.mike.frontend.taskapp.databinding.FragmentTaskUpdateBinding
import edu.mike.frontend.taskapp.model.Priority
import edu.mike.frontend.taskapp.model.TaskRequest
import edu.mike.frontend.taskapp.model.TaskResponse
import edu.mike.frontend.taskapp.viewmodel.PriorityViewModel
import edu.mike.frontend.taskapp.viewmodel.PriorityViewModelFactory
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel
import edu.mike.frontend.taskapp.viewmodel.TaskViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class TaskUpdate : Fragment() {


    // Definition of the binding variable
    private var _binding: FragmentTaskUpdateBinding? = null
    private val binding get() = _binding!!

    // View model
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var priorityViewModel: PriorityViewModel

    private lateinit var priorities: List<Priority>
    private lateinit var prioritySelected: Priority
    private lateinit var taskResponse: TaskResponse

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTaskUpdateBinding.inflate(inflater, container, false)

        val taskId : String = arguments?.getString(TaskAdapter.TASK_ID) ?: "0"

        // TaskViewModelFactory
        taskViewModel =
            ViewModelProvider(this, TaskViewModelFactory())[TaskViewModel::class.java]

        // Observer method to bind data of task into text views
        taskViewModel.taskResponse.observe(viewLifecycleOwner) {
            val formatter = SimpleDateFormat(BuildConfig.DATE_FORMAT)
            taskResponse = it
            binding.includeTaskForm.editTextTaskTitle.setText(it.title)
            binding.includeTaskForm.editTextTaskNotes.setText(it.notes)
            binding.includeTaskForm.textDueDate.setText(formatter.format(it.dueDate))
        }

        taskViewModel.getTask(taskId.toLong())

        priorityViewModel =
            ViewModelProvider(this, PriorityViewModelFactory())[PriorityViewModel::class.java]

        priorityViewModel.findAllPriorities()

        // Observer method to bind data
        priorityViewModel.priorityList.observe(viewLifecycleOwner) {
            priorities = it

            // access the spinner
            val adapter: ArrayAdapter<Priority> = ArrayAdapter<Priority>(
                activity?.applicationContext!!,
                android.R.layout.simple_spinner_item,
                priorities
            )

            binding.includeTaskForm.spinnerPriority.adapter = adapter

            binding.includeTaskForm.spinnerPriority.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?, position: Int, id: Long
                ) {
                    if (parent != null) {
                        if (parent.selectedItem != null) {
                            prioritySelected = parent.selectedItem as Priority
                        }
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

            if (taskResponse.priority.label != null) {
                val spinnerPosition: Int = adapter.getPosition(taskResponse.priority)
                binding.includeTaskForm.spinnerPriority.setSelection(spinnerPosition)
            }
        }

        binding.includeTaskForm.textDueDate.setOnClickListener {
            val datePickerBuilder: MaterialDatePicker.Builder<Long> = MaterialDatePicker
                .Builder
                .datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setTitleText("Select due date")
            val datePicker = datePickerBuilder.build()
            datePicker.show(parentFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener {
                val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                utc.timeInMillis = it
                //+1: The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0
                val stringData =
                    "${utc.get(Calendar.DAY_OF_MONTH)}/${utc.get(Calendar.MONTH) + 1}/${
                        utc.get(Calendar.YEAR)
                    }"
                binding.includeTaskForm.textDueDate.setText(stringData)
            }
        }

        binding.btnUpdate.setOnClickListener {
            val formatter = SimpleDateFormat(BuildConfig.DATE_FORMAT)
            val taskUpdated = TaskRequest(
                id = taskResponse.id,
                title = binding.includeTaskForm.editTextTaskTitle.text.toString(),
                notes = binding.includeTaskForm.editTextTaskNotes.text.toString(),
                dueDate = formatter.parse(binding.includeTaskForm.textDueDate.text.toString())!!,
                priority = prioritySelected
            )

            taskViewModel.createTask(taskUpdated)

            findNavController().navigate(R.id.action_taskUpdateScreen_to_taskListScreen2)
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}