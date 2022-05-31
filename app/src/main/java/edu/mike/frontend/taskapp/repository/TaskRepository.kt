package edu.mike.frontend.taskapp.repository

import edu.mike.frontend.taskapp.service.TaskService

class TaskRepository constructor(
    private val taskService: TaskService
){
    suspend fun getAllTask() = taskService.getAllTasks()

    suspend fun getTaskById(id : Long) = taskService.getTaskById(id)
}