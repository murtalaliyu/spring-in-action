package tacos.web;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.data.IngredientRepository;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

  private final IngredientRepository ingredientRepository;

  @Autowired
  public DesignTacoController(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }
  
  @ModelAttribute
  public void addIngredientsToModel(Model model) {
    Iterable<Ingredient> ingredients = ingredientRepository.findAll();

    Type[] types = Ingredient.Type.values();
    for (Type type : types) {
      model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
    }

    // I want to see what model looks like
    log.info("model: " + model);
  }

  @ModelAttribute(name = "tacoOrder")
  public TacoOrder order() {
    log.info("in tacoOrder...");
    return new TacoOrder();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    log.info("in taco...");
    return new Taco();
  }

  @GetMapping
  public String showDesignForm() {
    log.info("showing design page...");
    return "design";
  }

  @PostMapping
  public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
    if (errors.hasErrors()) {
      log.info("bro, you have taco errors: {}", taco);
      return "design";
    }
    
    tacoOrder.addTaco(taco);
    log.info("Processing taco: {}", taco);

    return "redirect:/orders/current";
  }

  private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
    return StreamSupport.stream(ingredients.spliterator(), false)
                        .filter(i -> i.getType().equals(type))
                        .collect(Collectors.toList());
  }

}
