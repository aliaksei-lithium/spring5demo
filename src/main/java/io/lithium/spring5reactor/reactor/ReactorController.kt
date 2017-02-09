package io.lithium.spring5reactor.reactor

import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono

// Check examples here: https://github.com/mix-it/mixit
/*
//Kotlin 1.1 support only
@Controller
class ReactorController(val handler: ReactorHandler) : RouterFunction<ServerResponse> {

    override fun route(request: ServerRequest) = route(request) {
        accept(TEXT_HTML).apply {
            (GET("/index/") or GET("/")) { index(request) }
        }
        accept(APPLICATION_JSON).apply {
            GET("/user/{login}") { userByName(request) }
        }
    }

    fun index(req: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().render("user")

    private fun userByName(request: ServerRequest): Mono<ServerResponse> {
        return handler.userByName(request)
    }
}*/
