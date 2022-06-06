package edu.mike.frontend.taskapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.mike.frontend.taskapp.model.TaskRequest
import edu.mike.frontend.taskapp.model.TaskResponse
import edu.mike.frontend.taskapp.repository.TaskRepository
import kotlinx.coroutines.*

sealed class StateTask {
    object Loading : StateTask()
    data class Success(val task: TaskResponse?) : StateTask()
    data class SuccessDelete(val deleted: Boolean?) : StateTask()
    data class SuccessList(val taskList: List<TaskResponse>?) : StateTask()
    data class Error(val message: String) : StateTask()
}

class TaskViewModel constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    // this is just a way to keep the mutable LiveData private, so it can't be updated
    private val _state = MutableLiveData<StateTask>()
    val state: LiveData<StateTask> get() = _state

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
    fun getTask(id: Long) {
        _state.value = StateTask.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = taskRepository.getTaskById(id)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) StateTask.Success(response.body())
                    else StateTask.Error("Error : ${response.message()} ")
                )
            }
        }
    }

    /**
     * When we call findAllTask() suspend method, then it suspends our coroutine.
     * The coroutine on the main thread will be resumed with the result as soon as the
     * withContext block is complete.
     */
    fun findAllTask() {
        _state.value = StateTask.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = taskRepository.getAllTask()
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) StateTask.SuccessList(response.body())
                    else StateTask.Error("Error : ${response.message()} ")
                )
            }
        }
    }

    fun deleteTaskById(id: Long) {
        _state.value = StateTask.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = taskRepository.deleteTaskById(id)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) StateTask.SuccessDelete(true)
                    else StateTask.Error("Error : ${response.message()} ")
                )
            }
        }
    }

    fun createTask(taskRequest: TaskRequest) {
        _state.value = StateTask.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = taskRepository.createTask(taskRequest)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    (if (response.isSuccessful) {
                        StateTask.Success(response.body() as TaskResponse)
                    } else {
                        StateTask.Error("Error : ${response.message()} ")
                        onError("Error : ${response.message()}")
                    }) as StateTask?
                )
            }
        }
    }

    fun updateTask(taskRequest: TaskRequest) {
        _state.value = StateTask.Loading
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = taskRepository.updateTask(taskRequest)
            withContext(Dispatchers.Main) {
                // if you're using postValue I don't think you need to switch to Dispatchers.Main?
                _state.postValue(
                    // when you get a response, the state is now either Success or Error
                    if (response.isSuccessful) StateTask.Success(response.body())
                    else StateTask.Error("Error : ${response.message()} ")
                )
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