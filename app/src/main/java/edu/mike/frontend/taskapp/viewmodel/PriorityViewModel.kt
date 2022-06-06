package edu.mike.frontend.taskapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.mike.frontend.taskapp.model.Priority
import edu.mike.frontend.taskapp.repository.PriorityRepository
import kotlinx.coroutines.*

class PriorityViewModel constructor(
    private val  priorityRepository: PriorityRepository
) : ViewModel() {

    val priorityList = MutableLiveData<List<Priority>>()

    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    fun findAllPriorities() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = priorityRepository.getAllPriorities()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    priorityList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}