package com.example.Orders.services.impl;

import com.example.Orders.bindings.OrderAddBindingModel;
import com.example.Orders.model.entities.OrderEntity;
import com.example.Orders.model.serviceModels.OrderServiceModel;
import com.example.Orders.repositories.OrderRepository;
import com.example.Orders.services.OrderService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    private final Gson gson;
    private final Resource productFile;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper mapper, Gson gson,
                            @Value("classpath:init/orders.json") Resource productFile) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.productFile = productFile;
    }

    @Override
    public void seedOrdersFromJson() throws IOException {
        if (this.orderRepository.count() == 0) {
            OrderAddBindingModel[] orders = this.gson.fromJson(Files.readString(Path.of(productFile.getURI())), OrderAddBindingModel[].class);
            for (OrderAddBindingModel order : orders) {
                this.orderRepository.save(this.mapper.map(order, OrderEntity.class));
            }
        }
    }

    @Override
    @RabbitListener(queues = "message_queue")
    public void seedOrder(OrderServiceModel orderServiceModel) {
        OrderEntity orderEntity = this.mapper.map(orderServiceModel, OrderEntity.class);
        try {
            this.orderRepository.save(orderEntity);
            logger.info("Recieved Message From RabbitMQ: " + orderServiceModel);
        } catch (Exception e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }
}
