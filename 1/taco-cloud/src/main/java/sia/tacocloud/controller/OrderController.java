package sia.tacocloud.controller;

import org.springframework.stereotype.Controller;
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
    System.out.println("in orderForm...");
    return "/orderForm";
  }

  @PostMapping
  public String processOrder(TacoOrder order, SessionStatus sessionStatus) {
    log.info("Order submitted: {}", order);
    return "redirect:/";
  }

}
