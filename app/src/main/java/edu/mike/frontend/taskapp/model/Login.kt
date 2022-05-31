package edu.mike.frontend.taskapp.model

data class LoginInput(
    var username: String,
    var password: String,
)

data class LoginFormState (
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)

data class LoginResult(
    var username: String,
    var password: String,
    var authorities: List<Authority>,
    var accountNonExpired : Boolean,
    var accountNonLocked : Boolean,
    var credentialsNonExpired : Boolean,
    var enabled : Boolean,
)

data class Authority (
    var authority: String,
)
