package com.example.Orders.web;

import com.example.Orders.model.serviceModels.OrderServiceModel;
import com.example.Orders.model.views.OrderViewModel;
import com.example.Orders.services.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper mapper;
    private final RabbitTemplate template;

    public OrderController(OrderService orderService, ModelMapper mapper, RabbitTemplate template) {
        this.orderService = orderService;
        this.mapper = mapper;
        this.template = template;
    }

    @PostMapping
    @ApiOperation(value = "Adding a order to the database", response = OrderViewModel.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<?> addOrders(@RequestBody OrderServiceModel orderServiceModel) {
        template.convertAndSend("message_exchange", "message_routingKEY", orderServiceModel);
        // OrderViewModel order = this.orderService.seedOrder(orderServiceModel);
        return new ResponseEntity<>(orderServiceModel, HttpStatus.CREATED);
    }
}
