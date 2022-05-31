package edu.mike.frontend.taskapp.repository

import edu.mike.frontend.taskapp.service.TaskService

class TaskRepository constructor(
    private val taskService: TaskService
){
    suspend fun getAllTask(authHeader:String) = taskService.getAllTasks(authHeader)

    suspend fun getTaskById(id : Long,authHeader:String) = taskService.getTaskById(id,authHeader)
}