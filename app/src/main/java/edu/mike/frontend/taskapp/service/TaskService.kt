package edu.mike.frontend.taskapp.service
import edu.mike.frontend.taskapp.model.Task
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TaskService {

    @GET("v1/tasks")
    suspend fun getAllTasks() : Response<List<Task>>

    @GET("v1/tasks/{id}")
    suspend fun getTaskById(@Path("id") id: Long) : Response<Task>

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