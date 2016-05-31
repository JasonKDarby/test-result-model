#TestResult - Model
I need a common model for test results, so: this.

To get it as a dependency (for now) you can add my [bintray repository](https://bintray.com/jasonkdarby/generic/model/view).
via gradle:
```groovy
repositories {
  maven { url 'https://dl.bintray.com/jasonkdarby/generic' }
}

dependencies {
  testCompile 'com.jdarb.testresult:model:0.3'
}
```

##Model

###Run
A run corresponds to a group of tests that would fully describe the state of an application/library.  It is equivalent to a full Jenkins pipeline (the testing aspects).

###Test
A test simultaneously corresponds to a whole collection of the high level concept of a test (unit, integration, functional, system, acceptance, what have you) as well as individual instances of those tests and components of those tests where it makes sense.

###Details
Details describe the components of a step, either tests or logs that describe the test.

###LogMessage
A log message is used to document the actions and/or intermediate results of a test.  In Spock you might use the block labels as log messages.