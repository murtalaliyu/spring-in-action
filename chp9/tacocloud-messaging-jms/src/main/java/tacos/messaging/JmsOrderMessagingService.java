package tacos.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.TacoOrder;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

    private JmsTemplate jms;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms) {
        this.jms = jms;
    }

    @Override
    public void sendOrder(TacoOrder order) {
        jms.send(session -> session.createObjectMessage(order));    // we must specify a default destination. See application.yml
    }
}
