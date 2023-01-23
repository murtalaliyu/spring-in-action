package tacos.web.api;

import lombok.Data;
import tacos.Ingredient;

import java.util.ArrayList;
import java.util.List;

@Data
public class TacoView {

    private Long id;
    private String name;
    private List<Ingredient> ingredients = new ArrayList<>();

    public TacoView(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

}
