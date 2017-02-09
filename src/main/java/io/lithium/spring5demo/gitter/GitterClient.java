package io.lithium.spring5demo.gitter;

import io.lithium.spring5demo.gitter.model.GitterMessage;
import io.lithium.spring5demo.gitter.model.GitterUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class GitterClient {
    private static final Logger _logger = LoggerFactory.getLogger(GitterClient.class.getSimpleName());
    private static final String GITTER_API_V1 = "https://api.gitter.im/v1/";
    private static final String GITTER_STREAM_API_V1 = "https://stream.gitter.im/v1/";

    private final WebClient webClient;

    public GitterClient(GitterProperties config) {
        this.webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector()).build()
                                  .filter(oAuthToken(config.getToken()));
    }

    public Flux<GitterUser> findUserInRoom(String userName, String roomId) {
        Flux<GitterUser> result = webClient.get()
                                           .uri(GITTER_API_V1 + "rooms/{roomId}/users?q={userName}", roomId, userName)
                                           .accept(MediaType.APPLICATION_JSON)
                                           .exchange()
                                           .publishOn(Schedulers.elastic())
                                           .log()
                                           .flatMap(clientResponse -> clientResponse.bodyToFlux(GitterUser.class));
        return result;
    }

    public Flux<GitterUser> getUsersInRoom(String roomId, int limit) {
        Flux<GitterUser> result = webClient.get()
                                           .uri(GITTER_API_V1 + "rooms/{roomId}/users?limit={limit}", roomId, limit)
                                           .accept(MediaType.APPLICATION_JSON)
                                           .exchange()
                                           .log()
                                           .flatMap(clientResponse -> clientResponse.bodyToFlux(GitterUser.class))
                                           .doOnError(throwable -> {
                                               _logger.error("Nice catch: " + throwable.getMessage());
                                           });
        return result;
    }

    public Flux<GitterMessage> streamMessages(final String roomId) {
        Flux<GitterMessage> stream = webClient.get()
                                              .uri(GITTER_STREAM_API_V1 + "rooms/{roomId}/chatMessages", roomId)
                                              .accept(MediaType.APPLICATION_JSON)
                                              .exchange()
                                              .flatMap(clientResponse -> clientResponse.bodyToFlux(GitterMessage.class));
        return stream;
    }

    private ExchangeFilterFunction oAuthToken(String token) {
        return (clientRequest, exchangeFunction) ->
                exchangeFunction
                        .exchange(ClientRequest.from(clientRequest).header("Authorization", "Bearer " + token).build());
    }
}
