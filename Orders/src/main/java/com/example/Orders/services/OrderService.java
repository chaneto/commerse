package com.example.Orders.services;

import com.example.Orders.model.serviceModels.OrderServiceModel;

import java.io.IOException;

public interface OrderService {

    void seedOrdersFromJson() throws IOException;

    void seedOrder(OrderServiceModel productServiceModel);
}
