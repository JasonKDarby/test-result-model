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
) : Timed, JSONizeable

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
    STARTED, PASS, FAIL, SKIP
}

data class Test(
        val name: String,
        val description: String,
        override val startTime: Instant,
        override val endTime: Instant,
        val state: State,
        val logMessages: List<LogMessage> = emptyList(),
        val children: List<Test> = emptyList()
) : Timed

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

interface JSONizeable {
    fun toJsonString(): String = gson.toJson(this)
}