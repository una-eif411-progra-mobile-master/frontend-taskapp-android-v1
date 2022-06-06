package edu.mike.frontend.taskapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.mike.frontend.taskapp.model.TaskRequest
import edu.mike.frontend.taskapp.model.TaskResponse
import edu.mike.frontend.taskapp.repository.TaskRepository
import kotlinx.coroutines.*

class TaskViewModel constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    val taskResponse =  MutableLiveData<TaskResponse>()
    val taskResponseList = MutableLiveData<List<TaskResponse>>()

    private var job: Job? = null
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    /**
     * When we call getTask() suspend method, then it suspends our coroutine.
     * The coroutine on the main thread will be resumed with the result as soon as the
     * withContext block is complete.
     */
    fun getTask(id:Long) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = taskRepository.getTaskById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    taskResponse.postValue(response.body())
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
            val response = taskRepository.getAllTask()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    taskResponseList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun deleteTaskById(id: Long){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            taskRepository.deleteTaskById(id)
        }
    }

    fun createTask(taskRequest : TaskRequest){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = taskRepository.createTask(taskRequest)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun updateTask(taskRequest : TaskRequest){
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = taskRepository.updateTask(taskRequest)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
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