package edu.mike.frontend.taskapp.service

import edu.mike.frontend.taskapp.model.LoginRequest
import edu.mike.frontend.taskapp.model.UserLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/v1/users/login")
    suspend fun login(@Body userLogin: LoginRequest) : Response<UserLoginResponse>

    companion object {
        var loginService : LoginService? = null
        fun getInstance() : LoginService {
            if (loginService == null) {
                loginService = ServiceBuilder.buildService(LoginService::class.java)
            }
            return loginService!!
        }
    }
}