package edu.mike.frontend.taskapp.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.mike.frontend.taskapp.R
import edu.mike.frontend.taskapp.model.*
import edu.mike.frontend.taskapp.repository.LoginRepository
import kotlinx.coroutines.*

class LoginViewModel constructor(
    private val loginRepository: LoginRepository,
) : ViewModel(){

    var job: Job? = null
    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResponse = MutableLiveData<LoginResult>()
    val loginResponse : LiveData<LoginResult> = _loginResponse

    fun login(loginRequest: LoginRequest) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = loginRepository.login(loginRequest)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _loginResponse.value =
                        LoginResult(success = LoggedInUserView(username = response.body()?.username
                            ?: ""))
                    loading.value = false
                } else {
                    _loginResponse.value = LoginResult(error = R.string.login_failed)
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun logout() {
        loginRepository.logout()
    }

    fun loginDataChanged(loginRequest: LoginRequest) {
        if (!isUserNameValid(loginRequest.username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(loginRequest.password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 5
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