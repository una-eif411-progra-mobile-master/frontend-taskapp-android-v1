package edu.mike.frontend.taskapp.repository

import edu.mike.frontend.taskapp.common.MyApplication
import edu.mike.frontend.taskapp.common.SessionManager
import edu.mike.frontend.taskapp.model.LoginRequest
import edu.mike.frontend.taskapp.model.LoginResponse
import edu.mike.frontend.taskapp.service.LoginService
import retrofit2.Response

class LoginRepository constructor (
    private val loginService: LoginService
    ){

    private var sessionManager: SessionManager = SessionManager(MyApplication.appContext!!)

    // in-memory cache of the loggedInUser object
    private var user: LoginResponse? = null

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        sessionManager.deleteAuthToken()
    }

    suspend fun login(userLogin: LoginRequest)  : Response<LoginResponse> {
        val response = loginService.login(userLogin)

        if (response.isSuccessful) {
            setLoggedInUser(response.body(), response.headers()["Authorization"].toString())
        }

        return response
    }

    private fun setLoggedInUser(loginRequest: LoginResponse?, token:String) {
        this.user = loginRequest
        sessionManager.saveAuthToken(token)

        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}