package com.jdarb.testresult.model

import org.jetbrains.spek.api.Spek
import java.time.Duration
import java.time.Instant
import kotlin.test.assertEquals

class TestResultModelSpek: Spek({

    given("A Run instance") {
        val startTime = Instant.EPOCH
        val interval = 1000L
        val endTime = startTime.plusMillis(interval)
        val run = Run(name = "test", startTime = startTime, endTime = endTime, id = "some")

        val jsonString: String = String(javaClass.classLoader.getResourceAsStream("0.json").readBytes())

        on("deriving the duration of the run") {
            val actualDuration = run.duration

            it("should result in a duration of $interval milliseconds") {
                assertEquals(Duration.ofMillis(interval), actualDuration)
            }

        }

        on("converting the run to a json string") {
            val actualJsonString = run.toJsonString()

            it("results in the expected output") {
                assertEquals(jsonString, actualJsonString)
            }

        }

        on("converting the json string to a run") {
            val parsedRun = parseRunFromJson(jsonString)

            it("converts into the initial Run") {
                assertEquals(run, parsedRun)
            }
        }

    }

})