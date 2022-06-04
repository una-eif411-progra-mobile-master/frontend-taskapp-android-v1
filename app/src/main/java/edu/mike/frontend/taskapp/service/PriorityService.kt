package edu.mike.frontend.taskapp.service

import edu.mike.frontend.taskapp.model.Priority
import retrofit2.Response
import retrofit2.http.GET

interface PriorityService {

    @GET("v1/priorities")
    suspend fun getAllPriorities(): Response<List<Priority>>

    /*
     * Function or any member of the class that can be called without having the instance of the
     * class then you can write the same as a member of a companion object inside the class
     */
    companion object {
        private var service: PriorityService? = null
        fun getInstance(): PriorityService {
            if (service == null) {
                service = ServiceBuilder.buildService(PriorityService::class.java)
            }
            return service!!
        }
    }
}