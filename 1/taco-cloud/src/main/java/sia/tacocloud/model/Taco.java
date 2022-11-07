package sia.tacocloud.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Taco {

  private Long id;
  private Date createdAt = new Date();

  @NotNull
  @Size(min=5, message="Name must be at least 5 characters long")
  private String name;

  @NotNull
  @Size(min=1, message="You must choose at least 1 ingredient")
  private List<IngredientRef> ingredients = new ArrayList<>();

  public void addIngredient(Ingredient ingredient) {
    this.ingredients.add(new IngredientRef(ingredient.getId()));
  }
  
}
