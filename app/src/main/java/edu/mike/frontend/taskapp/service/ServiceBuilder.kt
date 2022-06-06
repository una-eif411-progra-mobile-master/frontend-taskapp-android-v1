package edu.mike.frontend.taskapp.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import edu.mike.frontend.taskapp.BuildConfig
import edu.mike.frontend.taskapp.utils.AuthorizationInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * We create a builder of the retrofit object which can be reused for all method calls
 * declared in the RestApi interface.
 */
object ServiceBuilder {
    var gson: Gson = GsonBuilder()
        .setDateFormat(BuildConfig.DATE_FORMAT)
        .create()

    private val client =
        OkHttpClient.Builder().addInterceptor(AuthorizationInterceptor()).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL) // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}