package tacos.email;

import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderSubmitMessageHandler implements GenericHandler<EmailOrder> {

    private RestTemplate rest;
    private ApiProperties apiProps;

    public OrderSubmitMessageHandler(RestTemplate rest, ApiProperties apiProps) {
        this.rest = rest;
        this.apiProps = apiProps;
    }

    @Override
    public Object handle(EmailOrder order, MessageHeaders headers) {
        rest.postForObject(apiProps.getUrl(), order, String.class);
        return null;
    }
}
