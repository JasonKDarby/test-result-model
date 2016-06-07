package com.jdarb.testresult.model

import com.fatboyindustrial.gsonjavatime.Converters
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder
import org.everit.json.schema.loader.SchemaLoader
import org.json.JSONObject
import org.json.JSONTokener
import java.time.Duration
import java.time.Instant
import java.util.UUID

data class Run(
        val name: String,
        val tests: List<Test> = emptyList(),
        val id: String = UUID.randomUUID().toString(),
        override val startTime: Instant,
        override val endTime: Instant
) : Timed {
    fun toJsonString(): String = gson.toJson(this)
}

fun validateRunJson(jsonString: String): Unit =
        SchemaLoader
                .load(JSONObject(JSONTokener(Run::class.java.getResourceAsStream("ModelSchema.json"))))
                .validate(JSONObject(jsonString))

fun parseRunFromJson(jsonString: String): Run {
    validateRunJson(jsonString)
    return gson.fromJson<Run>(jsonString)
}

private val gson = Converters.registerInstant(GsonBuilder()).setPrettyPrinting().create()

enum class State {
    PASS, FAIL, SKIP
}

data class Test(
        val name: String,
        val description: String,
        override val startTime: Instant,
        override val endTime: Instant,
        val status: State,
        val details: Details = noDetails
) : Timed

data class Details(
        val logMessages: List<LogMessage> = emptyList(),
        val childTests: List<Test> = emptyList()
)

val noDetails = Details()

data class LogMessage(
        val text: String,
        val instant: Instant = Instant.now()
)

interface Timed {
    val startTime: Instant
    val endTime: Instant
    val duration: Duration
        get() = Duration.between(startTime, endTime)
}