package com.example.Orders.bindings;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderAddBindingModel {

    private Long id;

    @Expose
    @NotNull(message = "Product ID cannot be null!!!")
    @Min(1)
    private Long productId;

    @Expose
    @NotBlank(message = "Name cannot be empty string or null!!!")
    @Size(min = 1, message = "Name length must be more than 1 character!!!")
    private String product;

    @Expose
    @NotNull(message = "Quantity cannot be null!!!")
    @DecimalMin(value = "0", message = "Тhe quantity cannot be a negative value!!!")
    private BigDecimal quantity;

    @Expose
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "The create date cannot be null!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "The create date cannot be in the future!")
    private LocalDate createdDate;

    @Expose
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "The modified date cannot be in the future!")
    private LocalDate lastModifiedDate;

    public OrderAddBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
