package home.oleg

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockWebServerRule(val mockWebServer: MockWebServer) : TestRule {

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                mockWebServer.start()
                base?.evaluate()
                mockWebServer.shutdown()
            }

        }
    }

}