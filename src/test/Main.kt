import org.http4k.client.OkHttp
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.server.ApacheServer
import org.http4k.server.asServer

fun main() {
    val httpServer: HttpHandler = { request: Request ->
        Response(OK).body("Hello 🌍")
    }
    httpServer.asServer(ApacheServer(port = 8080)).start()

    val httpClient = OkHttp()
    println(httpClient(Request(GET, "http://localhost:8080")))
}
