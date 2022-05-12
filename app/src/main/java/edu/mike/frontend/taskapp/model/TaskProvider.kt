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
            ),
            Task(
                3,
                "Other Notes 3",
                "We can not create ViewModel on our own. We need the ViewModelProviders " +
                        "utility provided by Android to create ViewModels.",
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
                4,
                "Other Notes 4",
                "In the UI part, We need to create an instance of",
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
                5,
                "Other Notes 5",
                "Create recyclerview in our main XML file.",
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
                6,
                "Other Notes 6",
                "We need to create an instance of the ViewModel",
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
                7,
                "Other Notes 7",
                "Also, create an adapter for the recyclerview",
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
                8,
                "Other Notes 8",
                "Retrofit is a “Type-safe HTTP client for Android and Java”.",
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
                9,
                "Other Notes 9",
                "Both are part of the same class. RetrofitService.kt",
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
                10,
                "Other Notes 10",
                "Create the Retrofit service instance using the retrofit.",
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
        )
    }

}