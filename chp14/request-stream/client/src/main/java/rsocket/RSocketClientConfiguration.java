package rsocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

@Configuration
@Slf4j
public class RSocketClientConfiguration {

    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
        return args -> {
            String stockSymbol = "XYZ";

            RSocketRequester tcp = requesterBuilder.tcp("localhost", 7000);
            tcp
                    .route("stock/{symbol}", stockSymbol)
                    .retrieveFlux(StockQuote.class)
                    .doOnNext(stockQuote ->
                            log.info(
                                    "Price of {} : {} (at {})",
                                    stockQuote.getSymbol(),
                                    stockQuote.getPrice(),
                                    stockQuote.getTimestamp()
                            )
                    ).subscribe();
        };
    }

}
