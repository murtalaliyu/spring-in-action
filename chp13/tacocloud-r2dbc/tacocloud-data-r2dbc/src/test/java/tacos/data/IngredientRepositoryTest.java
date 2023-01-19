package tacos.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import tacos.Ingredient;

import tacos.Ingredient.Type;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
public class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepo;

    @BeforeEach
    public void setup() {
        Flux<Ingredient> deleteAndInsert = ingredientRepo.deleteAll()
                .thenMany(ingredientRepo.saveAll(
                        Flux.just(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                                new Ingredient("CHED", "Cheddar Cheese", Type.CHEESE)
                        )
                ));

        StepVerifier.create(deleteAndInsert)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void shouldSaveAndFetchIngredients() {
        StepVerifier.create(ingredientRepo.findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .consumeRecordedWith(ingredients -> {
                    assertThat(ingredients).hasSize(3);
                    assertThat(ingredients).contains(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
                    assertThat(ingredients).contains(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
                    assertThat(ingredients).contains(new Ingredient("CHED", "Cheddar Cheese", Type.CHEESE));
                })
                .verifyComplete();

        StepVerifier.create(ingredientRepo.findBySlug("FLTO"))
                .assertNext(ingredient -> {
                    ingredient.equals(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
                });
    }

}
