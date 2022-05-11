package com.example.Orders.model.entities;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique product identifier")
    private Long id;

    @Column(name = "product_id", nullable = false)
    @ApiModelProperty(value = "Product ID")
    private Long productId;

    @Column(nullable = false)
    @Size(min = 1)
    @ApiModelProperty(value = "Product name")
    private String product;

    @Column(nullable = false)
    @DecimalMin("0")
    @ApiModelProperty(value = "The available quantity of the product in the database")
    private BigDecimal quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_date", nullable = false)
    @ApiModelProperty(value = "Date of creation of the product")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "last_modified_date")
    @ApiModelProperty(value = "Last modification of the product")
    private LocalDate lastModifiedDate;

    public OrderEntity() {
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
