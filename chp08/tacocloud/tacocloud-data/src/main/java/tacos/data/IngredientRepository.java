package tacos.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import tacos.Ingredient;

@CrossOrigin(origins = "http://127.0.0.1:8080")
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
