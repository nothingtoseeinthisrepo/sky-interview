package com.test.interview.backend.core.dto

import kotlinx.serialization.Serializable

@Serializable
data class DescriptionDto(
    val description: String,
    val language: NamedApiResourceDto,
)
