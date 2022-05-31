package edu.mike.frontend.taskapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.mike.frontend.taskapp.repository.TaskRepository
import edu.mike.frontend.taskapp.service.TaskService

/**
 * if we need to pass some input data to the constructor of the viewModel,
 * we need to create a factory class for viewModel.
 */
@Suppress("UNCHECKED_CAST")
class TaskViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            TaskViewModel(
                taskRepository = TaskRepository(
                    taskService = TaskService.getInstance()
                )
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}