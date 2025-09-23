package com.test.interview.backend.core.dto

import kotlinx.serialization.Serializable

@Serializable
data class PageDto<T: Any>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>,
)
