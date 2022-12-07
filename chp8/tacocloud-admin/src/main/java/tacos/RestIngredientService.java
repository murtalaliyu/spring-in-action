package tacos;

import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RestIngredientService implements IngredientService {

    private RestTemplate restTemplate;
    private String ingredientUrl = "http://localhost:8080/data-api/ingredients";

    public RestIngredientService() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return Arrays.asList(restTemplate.getForObject(ingredientUrl, Ingredient.class));
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        return restTemplate.postForObject(ingredientUrl, ingredient, Ingredient.class);
    }
}
