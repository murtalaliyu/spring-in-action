package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tacos.Ingredient;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TacoCloudClient {

    private RestTemplate rest;
    //private Traverson traverson;

    private String ingredientsUrl = "http://127.0.0.1:8080/data-api/ingredients";
    private String ingredientByIdUrl = "http://127.0.0.1:8080/data-api/ingredients/{id}";

    public TacoCloudClient(RestTemplate rest) {
        this.rest = rest;
        //this.traverson = traverson;
    }

    /* ---------------------------- GET ---------------------------- */

    /*
    // Parameter as varargs
    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject(ingredientByIdUrl,
                Ingredient.class, ingredientId);
    }
    */

    /*
    // Parameter as map
    public Ingredient getIngredientById(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        return rest.getForObject(ingredientByIdUrl, Ingredient.class, urlVariables);
    }
    */

    /*
    // Request with URI instead of String
    public Ingredient getIngredientById(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        URI url = UriComponentsBuilder.fromHttpUrl(ingredientByIdUrl)
                .build(urlVariables);
        return rest.getForObject(url, Ingredient.class);
    }
    */

    // With getForEntity
    public Ingredient getIngredientById(String ingredientId) {
        ResponseEntity<Ingredient> responseEntity =
                rest.getForEntity(ingredientByIdUrl, Ingredient.class, ingredientId);
        log.info("Fetched time: {}", responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }

    // TODO: fix me
    public List<Ingredient> getAllIngredients() {
        return rest.exchange(ingredientsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {}).getBody();
    }

    /* ---------------------------- PUT ---------------------------- */

    // TODO: fix me
    public void updateIngredient(Ingredient ingredient) {
        rest.put(ingredientByIdUrl, ingredient, ingredient.getId());
    }

    /* ---------------------------- POST ---------------------------- */

    // With postForObject
    public Ingredient createIngredient(Ingredient ingredient) {
        return rest.postForObject(ingredientsUrl, ingredient, Ingredient.class);
    }
    /* ---------------------------- DELETE ---------------------------- */

    public void deleteIngredient(Ingredient ingredient) {
        rest.delete(ingredientByIdUrl, ingredient.getId());
    }
}
