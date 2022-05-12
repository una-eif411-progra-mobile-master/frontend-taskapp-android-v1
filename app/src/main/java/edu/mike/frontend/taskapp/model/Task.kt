package edu.mike.frontend.taskapp.model

import java.util.*

data class Task(
    var id: Long,
    var title: String,
    var notes: String,
    var createDate: Date,
    var dueDate: Date,
    var priority: Priority,
    var status: Status,
)