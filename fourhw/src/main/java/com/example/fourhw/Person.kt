package com.example.fourhw

import java.util.*

data class Person(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var typeContact: Boolean,
    var contact: String
)