package io.lithium.spring5reactor.reactor;

import io.lithium.spring5demo.gitter.GitterClient;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.ipc.netty.http.server.HttpServer;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

public class ReactorApplication {

    public static final String HOST = "localhost";
    public static final int PORT = 8090;

    private final GitterClient gitter;
    private final ReactorHandler handler;

    public ReactorApplication(final AnnotationConfigApplicationContext ctx) {
        gitter = ctx.getBean(GitterClient.class);
        handler = new ReactorHandler(gitter);
    }

    public static void main(String[] args) throws Exception {
        final AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(ReactorConfig.class);

        ReactorApplication server = new ReactorApplication(ctx);
        server.startReactorServer();

        System.out.println("Server started...");
        Thread.currentThread().join();
    }

    public RouterFunction<?> routingFunction() {

        return route(
                GET("/techtalk/").and(accept(APPLICATION_JSON)), handler::sayHello)
                .andRoute(
                        GET("/techtalk/gitter/{name}").and(accept(APPLICATION_JSON)), handler::userByName);
    }


    private void startReactorServer() {
        RouterFunction<?> route = routingFunction();
        HttpHandler httpHandler = toHttpHandler(route);

        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create(HOST, PORT);
        server.newHandler(adapter).block();
    }
}
