package tacos.messaging;

import tacos.TacoOrder;

public interface OrderMessagingService {

    public void sendOrder(TacoOrder order);

}
