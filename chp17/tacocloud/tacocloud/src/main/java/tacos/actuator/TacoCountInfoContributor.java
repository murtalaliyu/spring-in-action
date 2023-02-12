package tacos.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class TacoCountInfoContributor implements InfoContributor {

    private TacoRepository tacoRepo;
    private IngredientRepository ingredientRepo;

    public TacoCountInfoContributor(TacoRepository tacoRepo, IngredientRepository ingredientRepo) {
        this.tacoRepo = tacoRepo;
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public void contribute(Info.Builder builder) {
        long tacoCounter = tacoRepo.count().block();
        Map tacoMap = new HashMap<String, Object>();
        tacoMap.put("count", tacoCounter);
        builder.withDetail("taco-stats", tacoMap);

        long ingredientCounter = ingredientRepo.count().block();
        Map ingredientMap = new HashMap<String, Object>();
        ingredientMap.put("count", ingredientCounter);
        builder.withDetail("ingredient-stats", ingredientMap);
    }

}
