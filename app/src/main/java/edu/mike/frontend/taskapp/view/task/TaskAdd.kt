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
import edu.mike.frontend.taskapp.R
import edu.mike.frontend.taskapp.databinding.FragmentTaskAddBinding
import edu.mike.frontend.taskapp.model.Priority
import edu.mike.frontend.taskapp.model.TaskRequest
import edu.mike.frontend.taskapp.viewmodel.PriorityViewModel
import edu.mike.frontend.taskapp.viewmodel.PriorityViewModelFactory
import edu.mike.frontend.taskapp.viewmodel.TaskViewModel
import edu.mike.frontend.taskapp.viewmodel.TaskViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class TaskAdd : Fragment() {

    // Definition of the binding variable
    private var _binding: FragmentTaskAddBinding? = null
    private val binding get() = _binding!!

    // View model
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var priorityViewModel: PriorityViewModel

    private lateinit var priorities: List<Priority>
    private lateinit var prioritySelected: Priority

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
                        prioritySelected = parent.selectedItem as Priority
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }
            }
        }

        binding.textDueDate.setOnClickListener() {
            val today = MaterialDatePicker.todayInUtcMilliseconds()

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
                binding.textDueDate.setText(stringData)
            }
        }

        binding.btnCreate.setOnClickListener(){
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val text = binding.textDueDate.text.toString()
            val date = formatter.parse(text)
            taskViewModel.createTask(TaskRequest(
                title = binding.editTextTaskTitle.text.toString(),
                notes = binding.editTextTaskNotes.text.toString(),
                dueDate = date,
                priority = prioritySelected))
            findNavController().navigate(R.id.taskNav)
        }

        priorityViewModel.findAllPriorities()

        // Inflate the layout for this fragment
        return binding.root
    }
}