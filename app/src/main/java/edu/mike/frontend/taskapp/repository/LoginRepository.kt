package edu.mike.frontend.taskapp.repository

import edu.mike.frontend.taskapp.model.LoginRequest
import edu.mike.frontend.taskapp.service.LoginService

class LoginRepository constructor (
    private val loginService: LoginService
    ){

    suspend fun login(userLogin: LoginRequest) = loginService.login(userLogin)
}