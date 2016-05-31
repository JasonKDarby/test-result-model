package com.jdarb.testresult.model

import java.time.Duration
import java.time.Instant
import java.util.UUID

data class Run(
        val name: String,
        val startTime: Instant,
        val endTime: Instant,
        val tests: List<Test> = emptyList(),
        val id: String = UUID.randomUUID().toString()
)

//The type Duration is explicit here to disallow Duration? (nullable Duration that is)
val Run.duration: Duration
    get() = Duration.between(startTime, endTime)

enum class State {
    PASS, FAIL, SKIP
}

data class Test(
        val name: String,
        val description: String,
        val startTime: Instant,
        val endTime: Instant,
        val status: State,
        val details: Details = noDetails()
)

fun Test.duration() = Duration.between(startTime, endTime)

data class Details(
        val logMessages: List<LogMessage> = emptyList(),
        val childTests: List<Test> = emptyList()
)

fun noDetails() = Details()

data class LogMessage(
        val text: String,
        val instant: Instant = Instant.now()
)
