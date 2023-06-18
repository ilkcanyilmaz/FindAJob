package com.ilkcanyilmaz.findajob.data

data class User(
    val userId: String="",
    val name: String="",
    val surname: String="",
    val mail: String="",
    val phone: String="",
    val credit: Int=0,
    val gymId: Int=0
)
