package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import tacos.Ingredient;

import java.util.List;

@SpringBootConfiguration
@ComponentScan
@Slf4j
public class RestExamples {

    public static void main(String[] args) {
        SpringApplication.run(RestExamples.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner fetchIngredients(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("---------------------------- GET ----------------------------");
            log.info("GETTING INGREDIENT BY ID");
            log.info("Ingredient: " + tacoCloudClient.getIngredientById("CHED"));

            // TODO: fix me
//            log.info("GETTING ALL INGREDIENTS");
//            List<Ingredient> ingredients = tacoCloudClient.getAllIngredients();
//            log.info("ALL INGREDIENTS:");
//            for (Ingredient ingredient : ingredients) log.info("   - " + ingredient);
        };
    }

    // TODO: fix me
//    @Bean
//    public CommandLineRunner putAnIngredient(TacoCloudClient tacoCloudClient) {
//        return args -> {
//            log.info("---------------------------- PUT ----------------------------");
//            Ingredient before = tacoCloudClient.getIngredientById("LETC");
//            log.info("BEFORE: " + before);
//            tacoCloudClient.updateIngredient(new Ingredient("LETC", "Shredded Lettuce", Ingredient.Type.VEGGIES));
//            Ingredient after = tacoCloudClient.getIngredientById("LETC");
//            log.info("AFTER: ", after);
//        };
//    }

    @Bean
    public CommandLineRunner addAnIngredient(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("---------------------------- POST ----------------------------");
            Ingredient chix = new Ingredient("CHIX", "Shredded Chicken", Ingredient.Type.PROTEIN);
            Ingredient chixAfter = tacoCloudClient.createIngredient(chix);
            log.info("AFTER=1: " + chixAfter);
        };
    }

    // TODO: fix me
//    @Bean
//    public CommandLineRunner deleteAnIngredient(TacoCloudClient tacoCloudClient) {
//        return args -> {
//            log.info("---------------------------- DELETE ----------------------------");
//            Ingredient flto = tacoCloudClient.getIngredientById("FLTO");
//            log.info("ABOUT TO DELETE INGREDIENT: " + flto);
//            tacoCloudClient.deleteIngredient(flto);
//            log.info("DELETED INGREDIENT.");
//        };
//    }

}
