package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tacos.Ingredient;

@Service
@Slf4j
public class TacoCloudClient {

    private RestTemplate rest;
    //private Traverson traverson;

    public TacoCloudClient(RestTemplate rest) {
        this.rest = rest;
        //this.traverson = traverson;
    }

    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject("http://localhost:8080/data-api/ingredients/{id}",
                Ingredient.class, ingredientId);
    }

}
