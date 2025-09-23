package com.test.interview.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import javax.inject.Inject

@ProvidedTypeConverter
internal class ListTypeConverter @Inject constructor() {

    val json = Json

    @TypeConverter
    fun decode(value: String): List<String> {
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun encode(list: List<String>): String {
        return json.encodeToString(list)
    }
}