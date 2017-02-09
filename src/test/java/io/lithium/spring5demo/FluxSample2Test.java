package io.lithium.spring5demo;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class FluxSample2Test {

    @Test
    public void withDelay() {
        StepVerifier.withVirtualTime(() -> delayResult(30L))
                    .expectSubscription()
                    .thenAwait(Duration.ofSeconds(10))
                    .expectNoEvent(Duration.ofSeconds(10))
                    .thenAwait(Duration.ofSeconds(10))
                    .expectNext("FOO")
                    .consumeNextWith(s -> assertThat(s).isEqualTo("BAR"))
                    .expectComplete()
                    .verify();


    }

    // TODO: show delaySubscription and virtual time
    Flux<String> delayResult(Long delaySeconds) {
        return Flux.fromIterable(Arrays.asList("FOO", "BAR"))
                   .delaySubscription(Duration.ofSeconds(delaySeconds));

    }

    @Test
    public void hooks() {
        Hooks.onOperator(h -> h.operatorStacktrace());

        failFastAndTrack().subscribe(System.out::println);
    }

    // TODO: fail inside operator
    private Flux<String> failFastAndTrack() {
        return Flux.fromIterable(Arrays.asList("foo", "bar"))
                   .map(String::toUpperCase)
                   .map(s -> {
                    if (true) throw new RuntimeException();
                    return s;
                 }
                );
    }
}
