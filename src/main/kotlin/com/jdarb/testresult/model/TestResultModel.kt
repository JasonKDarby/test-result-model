package com.jdarb.testresult.model

import com.fatboyindustrial.gsonjavatime.Converters
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder
import java.time.Duration
import java.time.Instant
import java.util.UUID

data class Run(
        val name: String,
        val startTime: Instant,
        val endTime: Instant,
        val tests: List<Test> = emptyList(),
        val id: String = UUID.randomUUID().toString()
) {
    val duration: Duration
        get() = Duration.between(startTime, endTime)

    fun toJsonString(): String = gson.toJson(this)
}

fun parseRunFromJson(jsonString: String): Run = gson.fromJson<Run>(jsonString)

private val gson = Converters.registerInstant(GsonBuilder()).setPrettyPrinting().create()

enum class State {
    PASS, FAIL, SKIP
}

data class Test(
        val name: String,
        val description: String,
        val startTime: Instant,
        val endTime: Instant,
        val status: State,
        val details: Details = noDetails
) {
    val duration: Duration
        get() = Duration.between(startTime, endTime)
}

data class Details(
        val logMessages: List<LogMessage> = emptyList(),
        val childTests: List<Test> = emptyList()
)

val noDetails = Details()

data class LogMessage(
        val text: String,
        val instant: Instant = Instant.now()
)