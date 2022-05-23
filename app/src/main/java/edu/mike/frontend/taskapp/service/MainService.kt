package edu.mike.frontend.taskapp.service
import retrofit2.Response
import retrofit2.http.GET
import edu.mike.frontend.taskapp.model.Task
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MainService {

    @GET("api/v1/tasks")
    suspend fun getAllTasks() : Response<List<Task>>

    companion object{
        var mainService : MainService? = null
        fun getInstance() : MainService {
            if (mainService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://628ae68e667aea3a3e23e474.mockapi.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                mainService = retrofit.create(MainService::class.java)
            }
            return mainService!!
        }
    }
}