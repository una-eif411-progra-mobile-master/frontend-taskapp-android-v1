package edu.mike.frontend.taskapp.service
import edu.mike.frontend.taskapp.model.TaskRequest
import edu.mike.frontend.taskapp.model.TaskResponse
import retrofit2.Response
import retrofit2.http.*

interface TaskService {

    @GET("v1/tasks")
    suspend fun getAllTasks() : Response<List<TaskResponse>>

    @GET("v1/tasks/{id}")
    suspend fun getTaskById(@Path("id") id: Long) : Response<TaskResponse>

    @DELETE("v1/tasks/{id}")
    suspend fun deleteTaskById(@Path("id") id: Long)

    @POST("v1/tasks")
    suspend fun createTask(@Body taskRequest: TaskRequest) : Response<TaskResponse>

    @PUT("v1/tasks")
    suspend fun updateTask(@Body taskRequest: TaskRequest) : Response<TaskResponse>

    /*
     * Function or any member of the class that can be called without having the instance of the
     * class then you can write the same as a member of a companion object inside the class
     */
    companion object{
        private var taskService : TaskService? = null
        fun getInstance() : TaskService {
            if (taskService == null) {
                taskService = ServiceBuilder.buildService(TaskService::class.java)
            }
            return taskService!!
        }
    }
}