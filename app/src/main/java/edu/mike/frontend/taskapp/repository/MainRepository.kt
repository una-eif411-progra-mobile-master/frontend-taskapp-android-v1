package edu.mike.frontend.taskapp.repository

import edu.mike.frontend.taskapp.service.MainService

class MainRepository constructor(
    private val mainService: MainService
){
    suspend fun getAllTask() = mainService.getAllTasks()

    suspend fun getTaskById(id : Long) = mainService.getTaskById(id)
}