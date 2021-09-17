import org.http4k.client.OkHttp
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.ApacheServer
import org.http4k.server.asServer


class NameIsRequired : Exception()

val routes = routes(
    "/hello" bind GET to {
        Response(OK).body("Hello 🌍")
    },
    "/bye" bind GET to { request ->
        val name: String = request.query("name") ?: throw NameIsRequired()
        Response(OK).body("Bye, $name 👋")
    },
)

fun main() {
    val httpServer: HttpHandler = routes
    httpServer.asServer(ApacheServer(port = 8080)).start()

    val httpClient = OkHttp()
    println(httpClient(Request(GET, "http://localhost:8080/bye?nameee=Raul")))
}