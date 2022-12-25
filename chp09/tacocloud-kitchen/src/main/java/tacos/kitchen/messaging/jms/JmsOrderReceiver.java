package tacos.kitchen.messaging.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import tacos.TacoOrder;
import tacos.kitchen.OrderReceiver;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class JmsOrderReceiver implements OrderReceiver {

    private JmsTemplate jms;
//    private MessageConverter converter;

//    @Autowired
    public JmsOrderReceiver(JmsTemplate jms/*, MessageConverter converter*/) {
        this.jms = jms;
//        this.converter = converter;
    }

    @Override
    public TacoOrder receiveOrder() {
//        Message message = jms.receive("tacocloud.order.queue");
//        return (TacoOrder) converter.fromMessage(message);
        return (TacoOrder) jms.receiveAndConvert("tacocloud.order.queue");
    }
}
