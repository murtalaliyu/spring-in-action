package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import tacos.Ingredient;

import java.net.URI;
import java.util.List;

@SpringBootApplication
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
            log.info("----------------------- GET -------------------------");
            log.info("GETTING INGREDIENT BY IDE");
            log.info("Ingredient: " + tacoCloudClient.getIngredientById("CHED"));
            log.info("GETTING ALL INGREDIENTS");
            List<Ingredient> ingredients = tacoCloudClient.getAllIngredients();
            log.info("All ingredients:");
            for (Ingredient ingredient : ingredients) {
                log.info("   - " + ingredient);
            }
        };
    }

    @Bean
    public CommandLineRunner putAnIngredient(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- PUT -------------------------");
            Ingredient before = tacoCloudClient.getIngredientById("LETC");
            log.info("BEFORE:  " + before);
            tacoCloudClient.updateIngredient(new Ingredient("LETC", "Shredded Lettuce", Ingredient.Type.VEGGIES));
            Ingredient after = tacoCloudClient.getIngredientById("LETC");
            log.info("AFTER:  " + after);
        };
    }

    @Bean
    public CommandLineRunner addAnIngredient(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- POST -------------------------");
            Ingredient chix = new Ingredient("CHIX", "Shredded Chicken", Ingredient.Type.PROTEIN);
            Ingredient chixAfter = tacoCloudClient.createIngredient(chix);
            log.info("AFTER=1:  " + chixAfter);
            Ingredient beefFajita = new Ingredient("BFFJ", "Beef Fajita", Ingredient.Type.PROTEIN);
            URI uri = tacoCloudClient.createIngredient2(beefFajita);
            log.info("AFTER-2:  " + uri);
            Ingredient shrimp = new Ingredient("SHMP", "Shrimp", Ingredient.Type.PROTEIN);
            Ingredient shrimpAfter = tacoCloudClient.createIngredient3(shrimp);
            log.info("AFTER-3:  " + shrimpAfter);
        };
    }

    @Bean
    public CommandLineRunner deleteAnIngredient(TacoCloudClient tacoCloudClient) {
        return args -> {
            log.info("----------------------- DELETE -------------------------");
            Ingredient before = tacoCloudClient.getIngredientById("CHIX");
            log.info("BEFORE:  " + before);
            tacoCloudClient.deleteIngredient(before);
            Ingredient after = tacoCloudClient.getIngredientById("CHIX");
            log.info("AFTER:  " + after);
            before = tacoCloudClient.getIngredientById("BFFJ");
            log.info("BEFORE:  " + before);
            tacoCloudClient.deleteIngredient(before);
            after = tacoCloudClient.getIngredientById("BFFJ");
            log.info("AFTER:  " + after);
            before = tacoCloudClient.getIngredientById("SHMP");
            log.info("BEFORE:  " + before);
            tacoCloudClient.deleteIngredient(before);
            after = tacoCloudClient.getIngredientById("SHMP");
            log.info("AFTER:  " + after);
        };
    }

}
