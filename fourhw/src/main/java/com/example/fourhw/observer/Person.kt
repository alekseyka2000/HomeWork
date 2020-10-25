package com.example.fourhw.observer

import java.util.UUID

data class Person(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var typeContact: Boolean,
    var contact: String
)