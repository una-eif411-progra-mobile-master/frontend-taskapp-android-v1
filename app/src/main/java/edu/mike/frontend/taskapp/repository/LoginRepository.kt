package edu.mike.frontend.taskapp.repository

import edu.mike.frontend.taskapp.model.LoginInput
import edu.mike.frontend.taskapp.service.LoginService

class LoginRepository constructor (
    private val loginService: LoginService
    ){

    suspend fun login(userLogin: LoginInput) = loginService.login(userLogin)
}