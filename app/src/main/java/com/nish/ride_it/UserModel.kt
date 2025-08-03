package com.nish.ride_it

data class UserModel(
    val email: String? = null,
    val regNo: String? = null,
    val fullName: String? = null, // Matches the field name in your code
    val phone: String? = null,   // Matches the field name in your code
    val hostel: String? = null
) {
    // A no-argument constructor is necessary for Firebase to deserialize data back into this object
    constructor() : this(null, null, null, null, null)
}