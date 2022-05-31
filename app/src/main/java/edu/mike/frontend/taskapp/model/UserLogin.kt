package edu.mike.frontend.taskapp.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    var username: String,
    var password: String,
)

data class LoginResponse(
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