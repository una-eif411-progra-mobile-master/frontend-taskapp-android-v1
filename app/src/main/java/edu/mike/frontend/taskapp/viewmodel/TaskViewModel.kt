package edu.mike.frontend.taskapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.mike.frontend.taskapp.model.Task
import edu.mike.frontend.taskapp.model.TaskProvider

class TaskViewModel : ViewModel() {

    val task =  MutableLiveData<Task>()
    val taskList = MutableLiveData<List<Task>>()

    fun getTask() {
        val position = (0..9).random()
        val _task = TaskProvider.findTaskById(position)
        task.postValue(_task)
    }

    fun findAllTask() {
        val _taskList = TaskProvider.findAllTask()
        taskList.postValue(_taskList)
    }

}