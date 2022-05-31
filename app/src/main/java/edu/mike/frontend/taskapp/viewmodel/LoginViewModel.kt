package edu.mike.frontend.taskapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.mike.frontend.taskapp.model.LoginInput
import edu.mike.frontend.taskapp.model.LoginResult
import edu.mike.frontend.taskapp.repository.LoginRepository
import kotlinx.coroutines.*

class LoginViewModel constructor(
    private val loginRepository: LoginRepository,
) : ViewModel(){

    val loginResult = MutableLiveData<LoginResult>()
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun login(loginInput: LoginInput) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = loginRepository.login(loginInput)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    loginResult.postValue(response.body())
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