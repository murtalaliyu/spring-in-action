package tacos.kitchen.messaging.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import tacos.TacoOrder;
import tacos.kitchen.OrderReceiver;

@Component
public class RabbitOrderReceiver implements OrderReceiver {

    private RabbitTemplate rabbit;
    private MessageConverter converter;

    @Autowired
    public RabbitOrderReceiver(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
        this.converter = rabbit.getMessageConverter();
    }

    @Override
    public TacoOrder receiveOrder() {
//        Message message = rabbit.receive("tacocloud.order.queue");  // timeout is set in application.yml
//        return message != null
//                ? (TacoOrder) converter.fromMessage(message)
//                : null;

        return (TacoOrder) rabbit.receiveAndConvert();

        // The downside with the code below is the message converter must be an implementation of SmartMessageConverter
//        return rabbit.receiveAndConvert("tacocloud.order.queue", new ParameterizedTypeReference<TacoOrder>() {});
    }

}
