package com.jdarb.testresult.model

import java.time.Instant

enum class State {
    PASS, FAIL, SKIP
}

data class Test(val name: String, val status: State, val details: Details)

data class Details(val logMessages: List<LogMessage>, val childTests: List<Test>)

enum class MessageType {
    DOCUMENTATION, INSTANCE
}

data class LogMessage(val time: Instant, val messageType: MessageType, val text: String)
