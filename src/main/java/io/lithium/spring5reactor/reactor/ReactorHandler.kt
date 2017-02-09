package io.lithium.spring5reactor.reactor

import io.lithium.spring5demo.gitter.GitterClient
import io.lithium.spring5demo.gitter.model.GitterUser
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class ReactorHandler(private val gitter: GitterClient) {

    fun sayHello(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().body(Mono.just("Hello, TechTalk!"), String::class.java)
    }

    fun userByName(request: ServerRequest): Mono<ServerResponse> {
        val userName = request.pathVariable("name")
        val javaByChat = "54d13187db8155e6700f6873"
        return ServerResponse.ok()
                .body(gitter.findUserInRoom(userName, javaByChat), GitterUser::class.java)
    }
}
