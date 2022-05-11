package com.example.Products.services;

import com.example.Products.model.bindings.FilterBindingModel;
import com.example.Products.model.entities.ProductEntity;
import com.example.Products.model.serviceModels.OrderServiceModel;
import com.example.Products.model.serviceModels.ProductServiceModel;
import com.example.Products.model.views.ProductViewModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductViewModel> getAllProductsBySpecifications(Specification specification);

    Specification getAllSpecifications(List<FilterBindingModel> specifications);

    ProductViewModel findByName(String name);

    int getAllProductCount();

    void seedProductsFromJson() throws IOException;

    void setQuantity(BigDecimal quantity, Long id);

    boolean quantityIsEnough(Long id, BigDecimal quantityBuy);

    ProductEntity seedProduct(ProductServiceModel productServiceModel);

    boolean productIsExists(String name);

    List<ProductViewModel> getAllProducts(Integer pageNo, Integer pageSize, Sort sort);

    ProductViewModel getById(Long id);

    void deleteProductById(Long id);

    Sort getSorted(String orderBy, String direction);

    OrderServiceModel getOrderServiceModel(ProductViewModel productViewModel, BigDecimal quantityBuy);

    List<ProductViewModel> conversionToListViewModel(List<ProductEntity> products);

}
