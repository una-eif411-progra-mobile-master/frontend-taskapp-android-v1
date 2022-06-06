package edu.mike.frontend.taskapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.mike.frontend.taskapp.repository.PriorityRepository
import edu.mike.frontend.taskapp.service.PriorityService

@Suppress("UNCHECKED_CAST")
class PriorityViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PriorityViewModel::class.java)) {
            PriorityViewModel(
                priorityRepository = PriorityRepository(
                    priorityService = PriorityService.getInstance()
                )
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}