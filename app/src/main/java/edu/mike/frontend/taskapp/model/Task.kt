package edu.mike.frontend.taskapp.model

import java.util.*

data class TaskRequest(
    var id: Long? = null,
    var title: String,
    var notes: String,
    var dueDate: Date,
    var priority: Priority,
)

data class TaskResponse (
    var id: Long? = null,
    var title: String,
    var notes: String,
    var createDate: Date,
    var dueDate: Date,
    var priority: Priority,
    var status: Status,
)