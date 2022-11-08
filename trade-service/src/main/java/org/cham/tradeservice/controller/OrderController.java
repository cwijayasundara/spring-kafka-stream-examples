package org.cham.tradeservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.cham.tradeservice.domain.Order;
import org.cham.tradeservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    @ResponseBody
    public Flux<Order> all() {
        log.info("Inside OrderController.all()");
        return orderService.getAllOrders();
    }
}
