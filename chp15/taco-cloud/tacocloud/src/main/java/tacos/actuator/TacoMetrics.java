package tacos.actuator;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;
import tacos.Ingredient;
import tacos.Taco;

import java.util.List;

// TODO: not working (http://localhost:8080/actuator/metrics/tacocloud)
@Component
public class TacoMetrics extends AbstractRepositoryEventListener<Taco> {

    private MeterRegistry meterRegistry;

    public  TacoMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    protected void onAfterCreate(Taco taco) {
        List<Ingredient> ingredients = taco.getIngredients();
        for (Ingredient ingredient : ingredients) {
            meterRegistry.counter("tacocloud", "ingredient", ingredient.getId()).increment();
        }
    }

}
