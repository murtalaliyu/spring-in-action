package reactorfun;

import lombok.Data;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class FluxTransformingTests {

    @Test
    public void skipAFew() {
        Flux<String> countFlux = Flux.just("one", "two", "skip a few", "ninety nine", "one hundred").skip(3);
        StepVerifier.create(countFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    @Test
    public void skipAFewSeconds() {
        Flux<String> countFlux = Flux.just("one", "two", "skip a few", "ninety nine", "one hundred")
                .delayElements(Duration.ofSeconds(1))
                .skip(Duration.ofSeconds(4));

        StepVerifier.create(countFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    @Test
    public void takeAFew() {
        Flux<String> countFlux = Flux.just("one", "two", "skip a few", "ninety nine", "one hundred").take(2);
        StepVerifier.create(countFlux)
                .expectNext("one")
                .expectNext("two")
                .verifyComplete();
    }

    @Test
    public void takeForAWhile() {
        Flux<String> countFlux = Flux.just("one", "two", "skip a few", "ninety nine", "one hundred")
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofSeconds(3));
        StepVerifier.create(countFlux)
                .expectNext("one", "two")
                .verifyComplete();
    }

    @Test
    public void filter() {
        Flux<Integer> numberFlux = Flux.just(1, 2, 3, 4, 5, 6);
        Flux<Integer> evenFlux = numberFlux.filter(n -> n % 2 == 0);

        StepVerifier.create(evenFlux)
                .expectNext(2, 4, 6)
                .verifyComplete();
    }

    @Test
    public void distinct() {
        Flux<String> animalFlux = Flux.just("dog", "cat", "bird", "dog", "bird", "anteater");
        Flux<String> distinctFlux = animalFlux.distinct();

        StepVerifier.create(distinctFlux)
                .expectNext("dog", "cat", "bird", "anteater")
                .verifyComplete();
    }

    @Test
    public void map() {
        Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .map(n -> {
                   String[] split = n.split("\\s");
                   return new Player(split[0], split[1]);
                });

        StepVerifier.create(playerFlux)
                .expectNext(new Player("Michael", "Jordan"))
                .expectNext(new Player("Scottie", "Pippen"))
                .expectNext(new Player("Steve", "Kerr"))
                .verifyComplete();
    }

    @Test
    public void flatMap() {
        Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .flatMap(n -> Mono.just(n)
                        .map(p -> {
                            String[] split = p.split("\\s");
                            return new Player(split[0], split[1]);
                        })
                        .subscribeOn(Schedulers.parallel())
                );

        List<Player> playerList = Arrays.asList(
                new Player("Michael", "Jordan"),
                new Player("Scottie", "Pippen"),
                new Player("Steve", "Kerr")
        );

        StepVerifier.create(playerFlux)
                .expectNextMatches(p -> playerList.contains(p))
                .expectNextMatches(p -> playerList.contains(p))
                .expectNextMatches(p -> playerList.contains(p))
                .verifyComplete();
    }

    @Data
    private static class Player {
        private final String firstName;
        private final String lastName;
    }

}
