package reactorfun;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class FluxMergingTests {

    @Test
    public void mergeFluxes() {
        Flux<String> characterFlux = Flux
                .just("Brad Pitt", "Angelina Jolie", "Seth Rogen", "Ryan Miller")
                .delayElements(Duration.ofMillis(500));
        Flux<String> foodFlux = Flux
                .just("Rice", "Beans", "Cherries", "Chicken")
                .delaySubscription(Duration.ofMillis(250))
                .delayElements(Duration.ofMillis(500));

        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

        StepVerifier.create(mergedFlux)
                .expectNext("Brad Pitt")
                .expectNext("Rice")
                .expectNext("Angelina Jolie")
                .expectNext("Beans")
                .expectNext("Seth Rogen")
                .expectNext("Cherries")
                .expectNext("Ryan Miller")
                .expectNext("Chicken")
                .verifyComplete();
    }

    @Test
    public void zipFluxes() {
        Flux<String> characterFlux = Flux
                .just("Brad Pitt", "Angelina Jolie", "Seth Rogen", "Ryan Miller");
        Flux<String> foodFlux = Flux
                .just("Rice", "Beans", "Cherries", "Chicken");

        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);

        StepVerifier.create(zippedFlux)
                .expectNextMatches(p -> p.getT1().equals("Brad Pitt") && p.getT2().equals("Rice"))
                .expectNextMatches(p -> p.getT1().equals("Angelina Jolie") && p.getT2().equals("Beans"))
                .expectNextMatches(p -> p.getT1().equals("Seth Rogen") && p.getT2().equals("Cherries"))
                .expectNextMatches(p -> p.getT1().equals("Ryan Miller") && p.getT2().equals("Chicken"))
                .verifyComplete();
    }

    @Test
    public void zipFluxesToObject() {
        Flux<String> characterFlux = Flux
                .just("Brad Pitt", "Angelina Jolie", "Seth Rogen", "Ryan Miller");
        Flux<String> foodFlux = Flux
                .just("Rice", "Beans", "Cherries", "Chicken");

        Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);

        StepVerifier.create(zippedFlux)
                .expectNext("Brad Pitt eats Rice")
                .expectNext("Angelina Jolie eats Beans")
                .expectNext("Seth Rogen eats Cherries")
                .expectNext("Ryan Miller eats Chicken")
                .verifyComplete();
    }

    @Test
    public void firstWithSignalFlux() {
        Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth").delaySubscription(Duration.ofMillis(100));
        Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");

        Flux<String> firstFlux = Flux.firstWithSignal(slowFlux, fastFlux);

        StepVerifier.create(firstFlux)
                .expectNext("hare")
                .expectNext("cheetah")
                .expectNext("squirrel")
                .verifyComplete();
    }

}
