package tacos.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.TacoOrder;
import tacos.data.OrderRepository;
import tacos.messaging.OrderMessagingService;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {

    private OrderRepository repo;
    private OrderMessagingService orderMessages;
    private EmailOrderService emailOrderService;

    public OrderApiController(OrderRepository repo, OrderMessagingService orderMessages, EmailOrderService emailOrderService) {
        this.repo = repo;
        this.orderMessages = orderMessages;
        this.emailOrderService = emailOrderService;
    }

    @GetMapping(produces = "application/json")
    public Flux<TacoOrder> allOrders() {
        return repo.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TacoOrder> postOrder(@RequestBody TacoOrder order) {
        orderMessages.sendOrder(order);
        return repo.save(order);
    }

    @PostMapping(path = "fromEmail", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TacoOrder> postOrderFromEmail(@RequestBody Mono<EmailOrder> emailOrder) {
        Mono<TacoOrder> order = emailOrderService.convertEmailOrderToDomainOrder(emailOrder);
        order.subscribe(orderMessages::sendOrder);  // TODO: not ideal...work into reactive flow below
        return order.flatMap(repo::save);
    }

    @PutMapping(path="/{orderId}", consumes="application/json")
    public Mono<TacoOrder> putOrder(@RequestBody Mono<TacoOrder> order) {
        return order.flatMap(repo::save);
    }

    @PatchMapping(path="/{orderId}", consumes="application/json")
    public Mono<TacoOrder> patchOrder(@PathVariable("orderId") String orderId,
                                      @RequestBody TacoOrder patch) {

        return repo.findById(orderId)
                .map(order -> {
                    if (patch.getDeliveryName() != null) {
                        order.setDeliveryName(patch.getDeliveryName());
                    }
                    if (patch.getDeliveryStreet() != null) {
                        order.setDeliveryStreet(patch.getDeliveryStreet());
                    }
                    if (patch.getDeliveryCity() != null) {
                        order.setDeliveryCity(patch.getDeliveryCity());
                    }
                    if (patch.getDeliveryState() != null) {
                        order.setDeliveryState(patch.getDeliveryState());
                    }
                    if (patch.getDeliveryZip() != null) {
                        order.setDeliveryZip(patch.getDeliveryState());
                    }
                    if (patch.getCcNumber() != null) {
                        order.setCcNumber(patch.getCcNumber());
                    }
                    if (patch.getCcExpiration() != null) {
                        order.setCcExpiration(patch.getCcExpiration());
                    }
                    if (patch.getCcCVV() != null) {
                        order.setCcCVV(patch.getCcCVV());
                    }
                    return order;
                })
                .flatMap(repo::save);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") String orderId) {
        try {
            repo.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {}
    }


}
