package com.example.Orders.init;

import com.example.Orders.services.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInitializer implements CommandLineRunner {

    private final OrderService orderService;

    public DataBaseInitializer(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.orderService.seedOrdersFromJson();
    }
}
