package reactorfun;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

public class FluxCreationTests {

    @Test
    public void createAFlux_just() {
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
        stepVerifier(fruitFlux);
    }

    @Test
    public void createAFlux_fromArray() {
        String[] fruits = new String[]{"Apple", "Orange", "Grape", "Banana", "Strawberry"};
        Flux<String> fruitFlux = Flux.fromArray(fruits);
        stepVerifier(fruitFlux);
    }

    @Test
    public void createAFlux_fromIterable() {
        List fruits = List.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
        Flux<String> fruitFlux = Flux.fromIterable(fruits);
        stepVerifier(fruitFlux);
    }

    @Test
    public void createAFlux_fromStream() {
        Stream fruits = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
        Flux<String> fruitFlux = Flux.fromStream(fruits);
        stepVerifier(fruitFlux);
    }

    @Test
    public void createAFlux_fromRange() {
        Flux<Integer> rangeFlux = Flux.range(1, 5);
        StepVerifier.create(rangeFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }

    @Test
    public void createAFlux_fromInterval() {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);
        StepVerifier.create(intervalFlux)
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .expectNext(3L)
                .expectNext(4L)
                .verifyComplete();
    }

    private void stepVerifier(Flux<String> flux) {
        StepVerifier.create(flux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

}
