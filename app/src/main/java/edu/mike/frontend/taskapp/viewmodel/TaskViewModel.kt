package edu.mike.frontend.taskapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.mike.frontend.taskapp.model.Task
import edu.mike.frontend.taskapp.model.TaskProvider
import edu.mike.frontend.taskapp.repository.MainRepository
import kotlinx.coroutines.*

class TaskViewModel constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val task =  MutableLiveData<Task>()
    val taskList = MutableLiveData<List<Task>>()
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getTask() {
        val position = (0..9).random()
        val _task = TaskProvider.findTaskById(position)
        task.postValue(_task)
    }

    fun findAllTask() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getAllTask()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    taskList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
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