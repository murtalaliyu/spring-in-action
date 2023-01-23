package tacos.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tacos.TacoOrder;

@Service
@Slf4j
public class NoOpOrderMessagingService implements OrderMessagingService {

    @Override
    public void sendOrder(TacoOrder order) {
        log.info("Sending order to kitchen: " + order);
    }

}
