package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.service.OrderAdminService;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
  
  private OrderAdminService orderAdminService;

  public AdminController(OrderAdminService orderAdminService) {
    this.orderAdminService = orderAdminService;
  }

  @GetMapping
  public String showAdminPage() {
    return "admin";
  }

  @PostMapping("/deleteOrders")
  public String deleteAllOrders() {
    orderAdminService.deleteAllOrders();
    log.info(" --- deleted all orders!");
    return "redirect:/admin";
  }

}
