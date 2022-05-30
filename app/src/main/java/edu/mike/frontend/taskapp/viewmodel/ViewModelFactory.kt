package edu.mike.frontend.taskapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.mike.frontend.taskapp.repository.MainRepository

/**
 * if we need to pass some input data to the constructor of the viewModel,
 * we need to create a factory class for viewModel.
 */
class ViewModelFactory constructor(
    private val repository: MainRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            TaskViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
}

}