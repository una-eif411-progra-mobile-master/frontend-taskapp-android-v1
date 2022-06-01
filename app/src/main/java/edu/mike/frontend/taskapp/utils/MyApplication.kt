package edu.mike.frontend.taskapp.utils

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createSessionManager(SessionManager(applicationContext))
    }

    companion object {
        var sessionManager: SessionManager? = null

        fun createSessionManager(newInstance: SessionManager){
            sessionManager = newInstance
        }
    }
}
