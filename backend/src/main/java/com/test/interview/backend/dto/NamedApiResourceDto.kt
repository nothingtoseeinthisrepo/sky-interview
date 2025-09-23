package com.test.interview.backend.core.dto

import kotlinx.serialization.Serializable

@Serializable
data class NamedApiResourceDto(
    val name: String,
    val url: String,
)
