package sia.tacocloud.data;

import java.util.Optional;

import sia.tacocloud.model.Ingredient;

public interface IngredientRepository {

  Iterable<Ingredient> findAll();
  Optional<Ingredient> findById(String id);
  Ingredient save(Ingredient ingredient);
  
}
