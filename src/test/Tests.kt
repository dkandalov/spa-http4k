
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.junit.jupiter.api.Test

class BackendTests {
    @Test fun `hello world`() {

        val httpHandler: HttpHandler = routes

        val response = httpHandler(Request(GET, "/hello"))

        assertThat(response.status, equalTo(OK))
    }
}