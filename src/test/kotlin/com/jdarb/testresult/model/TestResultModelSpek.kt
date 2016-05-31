package com.jdarb.testresult.model

import org.jetbrains.spek.api.Spek
import java.time.Duration
import java.time.Instant
import kotlin.test.assertEquals

class TestResultModelSpek: Spek({

    given("A Run instance") {
        val startTime = Instant.now()
        val interval = 1000L
        val endTime = startTime.plusMillis(interval)
        val run = Run(name = "test", startTime = startTime, endTime = endTime)

        on("deriving the duration of the run") {
            val actualDuration = run.duration

            it("should result in a duration of $interval milliseconds") {
                assertEquals(Duration.ofMillis(interval), actualDuration)
            }

        }

    }

})