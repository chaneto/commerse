package com.example.Orders.model.entities;

import com.example.Orders.model.serviceModels.OrderServiceModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

public class Message {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonIgnore
    OrderServiceModel orderServiceModel;
    String message;
    String status;

    public Message() {
    }

    public OrderServiceModel getOrderServiceModel() {
        return orderServiceModel;
    }

    public void setOrderServiceModel(OrderServiceModel orderServiceModel) {
        this.orderServiceModel = orderServiceModel;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
