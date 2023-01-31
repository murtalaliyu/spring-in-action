package rsocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

import java.net.URI;

@Configuration
@Slf4j
public class RSocketClientConfiguration {

    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
        return args -> {
            RSocketRequester requester = requesterBuilder.websocket(URI.create("ws://localhost:8080/rsocket"));

            requester
                    .route("greeting")
                    .data("Hello RSocket!")
                    .retrieveMono(String.class)
                    .subscribe(response -> log.info("Got a response: {}", response));

            String who = "Murtala";
            requester
                    .route("greeting/{name}", who)
                    .data("Hello RSocket!")
                    .retrieveMono(String.class)
                    .subscribe(response -> log.info("Got a response: {}", response));
        };
    }

}
