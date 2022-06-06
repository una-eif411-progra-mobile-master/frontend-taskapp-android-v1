package edu.mike.frontend.taskapp.repository

import edu.mike.frontend.taskapp.model.TaskRequest
import edu.mike.frontend.taskapp.service.TaskService

class TaskRepository constructor(
    private val taskService: TaskService
){
    suspend fun getAllTask() = taskService.getAllTasks()

    suspend fun getTaskById(id : Long) = taskService.getTaskById(id)

    suspend fun deleteTaskById(id : Long) = taskService.deleteTaskById(id)

    suspend fun createTask(taskRequest: TaskRequest) = taskService.createTask(taskRequest)

    suspend fun updateTask(taskRequest: TaskRequest) = taskService.updateTask(taskRequest)
}