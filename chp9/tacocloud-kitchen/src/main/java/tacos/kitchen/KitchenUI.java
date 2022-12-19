package tacos.kitchen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tacos.TacoOrder;

// TODO: ??? Why does this exist?

@Component
@Slf4j
public class KitchenUI {

    public void displayOrder(TacoOrder order) {
        log.info("RECEIVED ORDER: " + order);
    }

}
