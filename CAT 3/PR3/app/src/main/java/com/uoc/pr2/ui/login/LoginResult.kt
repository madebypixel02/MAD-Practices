package com.uoc.pr2.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    var success: LoggedInUserView? = null,
    val error: Int? = null
)