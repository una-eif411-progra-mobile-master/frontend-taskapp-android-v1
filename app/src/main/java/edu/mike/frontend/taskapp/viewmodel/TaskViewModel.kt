package edu.mike.frontend.taskapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.mike.frontend.taskapp.model.Task
import edu.mike.frontend.taskapp.repository.TaskRepository
import kotlinx.coroutines.*

class TaskViewModel constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    val task =  MutableLiveData<Task>()
    val taskList = MutableLiveData<List<Task>>()
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    /**
     * When we call getTask() suspend method, then it suspends our coroutine.
     * The coroutine on the main thread will be resumed with the result as soon as the
     * withContext block is complete.
     */
    fun getTask() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val position : Int = (1..49).random()
            val response = taskRepository.getTaskById(position.toLong(),"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWN1cmUtYXBpIiwiYXVkIjoic2VjdXJlLWFwcCIsInN1YiI6InVzZXJAZ3V6bWFuYWxhbi5jb20iLCJleHAiOjE2NTQ4NDMxNDV9.EtaSw0rrgF8Xa5mW1EQxBYzuTDb1LRADv6OXQZdZN-1_f_YQMGVVbNOIhEgEJzArmRdk8lkUVTyQMpsk0QxHcA")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    task.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    /**
     * When we call findAllTask() suspend method, then it suspends our coroutine.
     * The coroutine on the main thread will be resumed with the result as soon as the
     * withContext block is complete.
     */
    fun findAllTask() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = taskRepository.getAllTask("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWN1cmUtYXBpIiwiYXVkIjoic2VjdXJlLWFwcCIsInN1YiI6InVzZXJAZ3V6bWFuYWxhbi5jb20iLCJleHAiOjE2NTQ4NDMxNDV9.EtaSw0rrgF8Xa5mW1EQxBYzuTDb1LRADv6OXQZdZN-1_f_YQMGVVbNOIhEgEJzArmRdk8lkUVTyQMpsk0QxHcA")
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