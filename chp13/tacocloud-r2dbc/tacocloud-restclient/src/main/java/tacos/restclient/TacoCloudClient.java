package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tacos.Ingredient;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TacoCloudClient {

    private RestTemplate rest;
    private String ingredientURL = "http://localhost:8080/ingredients";

    public TacoCloudClient(RestTemplate rest) {
        this.rest = rest;
    }

    //
    // GET examples
    //

    /* Specify parameter as varargs argument */
    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject(ingredientURL+"/{id}", Ingredient.class, ingredientId);
    }

    /* Specify parameters with a map */
    public Ingredient getIngredientById2(String ingredientId) {
        Map ingredientMap = new HashMap<String, String>();
        ingredientMap.put("id", ingredientId);
        return rest.getForObject(ingredientURL+"/{id}", Ingredient.class, ingredientMap);
    }

    /* Request with URI instead of String */
    public Ingredient getIngredientById3(String ingredientId) {
        Map ingredientMap = new HashMap<String, String>();
        ingredientMap.put("id", ingredientId);
        URI url = UriComponentsBuilder.fromHttpUrl(ingredientURL+"/{id}").build(ingredientMap);
        return rest.getForObject(url, Ingredient.class);
    }

    /* Use getForEntity() instead of getForObject */
    public Ingredient getIngredientById4(String ingredientId) {
        ResponseEntity<Ingredient> responseEntity = rest.getForEntity(ingredientURL+"/{id}", Ingredient.class, ingredientId);
        log.info("Fetched time: " + responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }

    public List<Ingredient> getAllIngredients() {
        return rest.exchange(ingredientURL, HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {}).getBody();
    }

    //
    // PUT examples
    //

    public void updateIngredient(Ingredient ingredient) {
        rest.put(ingredientURL+"/{id}", ingredient, ingredient.getId());
    }

    //
    // POST examples
    //

    public Ingredient createIngredient(Ingredient ingredient) {
        return rest.postForObject(ingredientURL, ingredient, Ingredient.class);
    }

    public URI createIngredient2(Ingredient ingredient) {
        return rest.postForLocation(ingredientURL, ingredient, Ingredient.class);
    }

    public Ingredient createIngredient3(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity = rest.postForEntity(ingredientURL, ingredient, Ingredient.class);
        log.info("New resource created at " + responseEntity.getHeaders().getLocation());
        return responseEntity.getBody();
    }

    //
    // DELETE examples
    //

    public void deleteIngredient(Ingredient ingredient) {
        rest.delete(ingredientURL+"/{id}", ingredient.getId());
    }

}
