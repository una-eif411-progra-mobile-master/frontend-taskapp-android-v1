package edu.mike.frontend.taskapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.mike.frontend.taskapp.model.Task
import edu.mike.frontend.taskapp.model.TaskProvider

class TaskViewModel : ViewModel() {

    val task =  MutableLiveData<Task>()

    fun getTask() {
        val _task = TaskProvider.findTaskById(1)
        task.postValue(_task)
    }

}