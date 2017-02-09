package io.lithium.spring5demo.sample;

import io.lithium.spring5demo.gitter.*;
import io.lithium.spring5demo.gitter.model.GitterUser;
import io.lithium.spring5demo.gitter.model.Mate;
import io.lithium.spring5demo.mongo.MateReactiveRepository;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
public class SampleController {
    private final DataBufferFactory factory = new DefaultDataBufferFactory();
    private final GitterProperties props;
    private final GitterClient gitterClient;
    private final MateReactiveRepository repository;

    public SampleController(GitterProperties props, MateReactiveRepository repository) {
        this.props = props;
        this.gitterClient = new GitterClient(props);
        this.repository = repository;
    }

    @GetMapping("/hello")
    public Mono<String> sayHello() {
        return Mono.just("Hello, JavaTechTalk!");
    }

    @GetMapping("/exchange")
    public Mono<Void> exchange(ServerWebExchange webExchange) {
        ServerHttpResponse response = webExchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        DataBuffer dataBuffer = factory.allocateBuffer().write("Using exchange!".getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }

    @GetMapping("/gitter")
    public Flux<GitterUser> gitterAll(@RequestParam Integer limit) {
        return gitterClient.getUsersInRoom(props.getRoom(), limit)
                           .map(gitterUser -> {
                               gitterUser.setDisplayName("XXX");
                               return gitterUser;
                           });
    }

    @GetMapping("/gitter/{name}")
    public Mono<GitterUser> gitter(@PathVariable String name) {
        return gitterClient.findUserInRoom(name, props.getRoom()).last();
    }

    @GetMapping("/mate")
    public Flux<Mate> count(@RequestParam Integer limit) {
        Flux<Mate> goodGuys = repository.findAll();
        Flux<GitterUser> usersInRoom = gitterClient.getUsersInRoom(props.getRoom(), limit);
        Mono<List<GitterUser>> listOfUsers = usersInRoom.collectList();

        return listOfUsers.flatMap(gitterUsers -> {
            return goodGuys.map(mate -> {
                Optional<GitterUser> first = gitterUsers.stream()
                                                        .filter(u -> u.getUsername().equals(mate.nickname))
                                                        .findFirst();
                return new Mate(mate.nickname, mate.firstName, first.isPresent());
            });
        });
    }

   /* @ExceptionHandler
    public ResponseEntity handleExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }*/
}
