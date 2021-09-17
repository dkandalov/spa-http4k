import org.http4k.client.OkHttp
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.I_M_A_TEAPOT
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.ApacheServer
import org.http4k.server.asServer


class NameIsRequired : Exception()

val filter = Filter { handler: HttpHandler ->
    val wrapperHandler : HttpHandler = { request ->
        println("Inside the filter")
        try {
            val response = handler.invoke(request)
            println("After the filter")
            response
        } catch (e: NameIsRequired) {
            Response(I_M_A_TEAPOT)
        }
    }
    wrapperHandler
}

val routes = routes(
    "/hello" bind GET to {
        Response(OK).body("Hello 🌍")
    },
    "/bye" bind GET to { request ->
        val name: String = request.query("name") ?: throw NameIsRequired()
        Response(OK).body("Bye, $name 👋")
    },
).withFilter(filter)

fun main() {
    val httpServer: HttpHandler = routes
    httpServer.asServer(ApacheServer(port = 8080)).start()

    val httpClient = OkHttp()
    println(httpClient(Request(GET, "http://localhost:8080/bye?nameee=Raul")))
}