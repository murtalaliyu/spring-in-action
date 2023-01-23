package tacos.web.api;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tacos.*;
import tacos.data.IngredientRepository;
import tacos.data.PaymentMethodRepository;
import tacos.data.UserRepository;

import java.util.List;

import tacos.web.api.EmailOrder.EmailTaco;

@Service
public class EmailOrderService {

    private UserRepository userRepo;
    private IngredientRepository ingredientRepo;
    private PaymentMethodRepository paymentMethodRepo;

    public EmailOrderService(UserRepository userRepo, IngredientRepository ingredientRepo, PaymentMethodRepository paymentMethodRepo) {
        this.userRepo = userRepo;
        this.ingredientRepo = ingredientRepo;
        this.paymentMethodRepo = paymentMethodRepo;
    }

    public Mono<TacoOrder> convertEmailOrderToDomainOrder(Mono<EmailOrder> emailOrder) {
        // TODO: Probably should handle unhappy case where email address doesn't match a given user or
        //       where the user doesn't have at least one payment method.

        return emailOrder.flatMap(eOrder -> {
            Mono<User> userMono = userRepo.findByEmail(eOrder.getEmail());
            Mono<PaymentMethod> paymentMono = userMono.flatMap(user -> {
                return paymentMethodRepo.findByUserId(user.getId());
            });

            return Mono.zip(userMono, paymentMono)
                    .flatMap(tuple -> {
                        User user = tuple.getT1();
                        PaymentMethod paymentMethod = tuple.getT2();
                        TacoOrder order = new TacoOrder();
                        // TODO: revisit this
                        // order.setUser(user);
                        order.setCcNumber(paymentMethod.getCcNumber());
                        order.setCcCVV(paymentMethod.getCcCVV());
                        order.setCcExpiration(paymentMethod.getCcExpiration());
                        order.setDeliveryName(user.getFullname());
                        order.setDeliveryStreet(user.getStreet());
                        order.setDeliveryCity(user.getCity());
                        order.setDeliveryState(user.getState());
                        order.setDeliveryZip(user.getZip());
                        // TODO: revisit this
                        // order.setPlacedAt(new Date());

                        return emailOrder.map(eOrd -> {
                            List<EmailTaco> emailTacos = eOrd.getTacos();
                            for (EmailTaco emailTaco : emailTacos) {
                                Taco taco = new Taco();
                                taco.setName(emailTaco.getName());

                                List<String> ingredientIds = emailTaco.getIngredients();
                                for (String ingredientId : ingredientIds) {
                                    Mono<Ingredient> ingredientMono = ingredientRepo.findBySlug(ingredientId);
                                    ingredientMono.subscribe(ingredient -> taco.addIngredient(ingredient));
                                }
                                order.addTaco(taco);
                            }
                            return order;
                        });
                    });
        });
    }

}
