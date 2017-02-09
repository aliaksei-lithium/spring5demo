package io.lithium.spring5demo;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;

public class FluxSampleTest {

    @Test
    public void empty() {
        Flux<String> flux = emptyFlux();

        StepVerifier.create(flux)
                    .expectComplete()
                    .verify();
    }

    // TODO Return an empty Flux
    Flux<String> emptyFlux() {
        return Flux.empty();
    }









    @Test
    public void fromValues() {
        Flux<String> flux = fooBarFluxFromValues();
        StepVerifier.create(flux)
                    .expectNext("foo", "bar")
                    .expectComplete()
                    .verify();
    }

    // TODO Return a Flux that contains 2 values "foo" and "bar"
    Flux<String> fooBarFluxFromValues() {
        return Flux.fromIterable(Arrays.asList("foo", "bar"));
    }








    @Test
    public void countEach100ms() {
        Flux<Long> flux = counter();
        StepVerifier.create(flux)
                    .expectNext(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L)
                    .expectComplete()
                    .verify();
    }

    // TODO Create a Flux that emits increasing values from 0 to 9 each 100ms //interval
    Flux<Long> counter() {
        return Flux.interval(Duration.ofMillis(100)).take(10);
    }








    @Test
    public void error() {
        Flux<String> flux = errorFlux();
        StepVerifier.create(flux)
                    .expectError(NullPointerException.class)
                    .verify();
    }

    // TODO Create a Flux that emits an IllegalStateException
    Flux<String> errorFlux() {
        return Flux.error(new NullPointerException("Boo!"));
    }
}
