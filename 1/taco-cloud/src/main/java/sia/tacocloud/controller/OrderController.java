package sia.tacocloud.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.model.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
  
  @GetMapping("/current")
  public String orderForm() {
    log.info("showing orderForm page...");
    return "/orderForm";
  }

  @PostMapping
  public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
    if (errors.hasErrors()) {
      log.info(":( errors exist in tacoOrder: {}", order);
      log.info("the errors are: {}", errors);
      return "orderForm";
    }
    
    log.info("Order submitted: {}", order);
    sessionStatus.setComplete();

    return "redirect:/";
  }

}
