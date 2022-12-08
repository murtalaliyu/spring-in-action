package tacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class RestIngredientService implements IngredientService {

    private RestTemplate restTemplate;
    private String ingredientUrl = "http://127.0.0.1:8080/data-api/ingredients";

    public RestIngredientService(String accessToken) {
        this.restTemplate = new RestTemplate();

        // attach request interceptor to the RestTemplate
        if (accessToken != null) {
            this.restTemplate.getInterceptors().add(getBearerTokenInterceptor(accessToken));
        }
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        ClientHttpRequestInterceptor interceptor = new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
                request.getHeaders().add("Authorization", "Bearer " + accessToken);
                return execution.execute(request, bytes);
            }
        };
        return interceptor;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        Iterable<Ingredient> ingredients = Arrays.asList(restTemplate.getForObject(ingredientUrl, Ingredient.class));
        log.info("INGREDIENTS: {}", ingredients);
        return ingredients;
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        return restTemplate.postForObject(ingredientUrl, ingredient, Ingredient.class);
    }
}
