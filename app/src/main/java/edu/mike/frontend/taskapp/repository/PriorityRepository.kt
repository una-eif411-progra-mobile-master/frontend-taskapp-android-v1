package edu.mike.frontend.taskapp.repository

import edu.mike.frontend.taskapp.service.PriorityService

class PriorityRepository constructor(
    private val priorityService: PriorityService
){
    suspend fun getAllPriorities() = priorityService.getAllPriorities()
}