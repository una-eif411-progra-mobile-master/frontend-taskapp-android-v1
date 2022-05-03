package edu.mike.frontend.taskapp.model

import java.util.*

/**
 * This is a temporary class to simulate the interaction with
 * data
 */
class TaskProvider {
    companion object {
        fun findTaskById(id: Int) : Task {
            return taskList[id]
        }

        fun findAllTask() : List<Task> {
            return taskList
        }

        val taskList = listOf<Task>(
            Task(
                1,
                "Different Notes",
                "Evaluate Students",
                Date(),
                Date(),
                Priority(
                    1,
                    "High",
                ),
                Status(
                    1,
                    "Pending",
                ),
            ),
            Task(
                2,
                "More Notes",
                "Coordinate with professors",
                Date(),
                Date(),
                Priority(
                    1,
                    "High",
                ),
                Status(
                    1,
                    "Pending",
                ),
            )
        )
    }

}