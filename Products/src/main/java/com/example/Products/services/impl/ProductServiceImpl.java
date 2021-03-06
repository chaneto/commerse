package com.example.Products.services.impl;

import com.example.Products.model.bindings.FilterBindingModel;
import com.example.Products.model.bindings.ProductAddBindingModel;
import com.example.Products.model.entities.ProductEntity;
import com.example.Products.model.serviceModels.OrderServiceModel;
import com.example.Products.model.serviceModels.ProductServiceModel;
import com.example.Products.model.views.ProductViewModel;
import com.example.Products.queryDsl.ProductPredicate;
import com.example.Products.queryDsl.SearchCriteria;
import com.example.Products.repositories.ProductRepository;
import com.example.Products.services.ProductService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    private final Gson gson;
    private final Resource productFile;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper, Gson gson
            , @Value("classpath:init/products.json") Resource productFile) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.productFile = productFile;
    }

    @Override
    public void seedProductsFromJson() throws IOException {
        if (this.productRepository.count() == 0) {
            ProductAddBindingModel[] products = this.gson.fromJson(Files.readString(Path.of(productFile.getURI())), ProductAddBindingModel[].class);
            for (ProductAddBindingModel product : products) {
                if (findByName(product.getName()) == null) {
                    seedProduct(this.mapper.map(product, ProductServiceModel.class));
                }
            }
        }
    }

    @Override
    public ProductViewModel findByName(String name) {
        ProductViewModel productViewModel = null;
        if (this.productRepository.findByName(name) != null) {
            productViewModel = this.mapper.map(this.productRepository.findByName(name), ProductViewModel.class);
        }
        return productViewModel;
    }


    @Override
    public int getAllProductCount() {
        return (int) this.productRepository.count();
    }

    @Override
    public void setQuantity(BigDecimal quantity, Long id) {
        BigDecimal newQuantity = getById(id).getQuantity().subtract(quantity);
        this.productRepository.setQuantity(newQuantity, id);
    }

    @Override
    public ProductEntity seedProduct(ProductServiceModel productServiceModel) {
        ProductEntity productEntity = this.mapper.map(productServiceModel, ProductEntity.class);
        try {
            this.productRepository.save(productEntity);
            return productEntity;
        } catch (Exception e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }

    }

    @Override
    public boolean productIsExists(String name) {
        return this.productRepository.findByName(name) != null;
    }

    @Override
    public ProductViewModel getById(Long id) {
        ProductEntity productEntity = this.productRepository.getById(id);
        if (productEntity != null) {
            return this.mapper.map(this.productRepository.getById(id), ProductViewModel.class);
        } else {
            return null;
        }
    }

    @Override
    public void deleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public List<ProductViewModel> getAllProducts(Integer pageNo, Integer pageSize, Sort sort) {
        Pageable paging = PageRequest.of(pageNo, pageSize, sort);

        Page<ProductEntity> pagedResult = this.productRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return conversionToListViewModel(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ProductViewModel> getAllProductsBySpecifications(Specification specification) {
        return conversionToListViewModel(this.productRepository.findAll(specification));
    }

    @Override
    public Specification getAllSpecifications(List<FilterBindingModel> specifications) {
        Specification specification = null;

        for (int i = 0; i < specifications.size(); i++) {
            String field = specifications.get(i).getField();
            String operation = specifications.get(i).getOperation();
            String value = specifications.get(i).getValue();
            SearchCriteria searchCriteria = new SearchCriteria(field, operation, value);
            ProductPredicate predicate = new ProductPredicate(searchCriteria);

            if (specification == null) {
                specification = Specification.where(predicate);
            } else {
                specification = specification.and(predicate);
            }
        }

        return specification;
    }

    @Override
    public OrderServiceModel getOrderServiceModel(ProductViewModel productViewModel, BigDecimal quantityBuy) {
        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setProductId(productViewModel.getId());
        orderServiceModel.setProduct(productViewModel.getName());
        orderServiceModel.setQuantity(quantityBuy);
        orderServiceModel.setCreatedDate(productViewModel.getCreatedDate());
        orderServiceModel.setLastModifiedDate(productViewModel.getLastModifiedDate());
        return orderServiceModel;
    }

    @Override
    public Sort getSorted(String orderBy, String direction) {
        Sort sortBy = null;
        if (orderBy.equals("id") && direction.equals("ASC")) {
            sortBy = Sort.by("id");
        } else if (orderBy.equals("id") && direction.equals("DESC")) {
            sortBy = Sort.by("id").descending();
        } else if (orderBy.equals("name") && direction.equals("ASC")) {
            sortBy = Sort.by("name");
        } else if (orderBy.equals("name") && direction.equals("DESC")) {
            sortBy = Sort.by("name").descending();
        } else if (orderBy.equals("category") && direction.equals("ASC")) {
            sortBy = Sort.by("category");
        } else if (orderBy.equals("category") && direction.equals("DESC")) {
            sortBy = Sort.by("category").descending();
        } else if (orderBy.equals("createdDate") && direction.equals("ASC")) {
            sortBy = Sort.by("createdDate");
        } else if (orderBy.equals("createdDate") && direction.equals("DESC")) {
            sortBy = Sort.by("createdDate").descending();
        }
        return sortBy;
    }

    @Override
    public boolean quantityIsEnough(Long id, BigDecimal quantityBuy) {
        BigDecimal productQuantity = getById(id).getQuantity();
        boolean result = false;
        int compare = productQuantity.compareTo(quantityBuy);
        if (compare == 0) {
            result = true;
        } else if (compare == 1) {
            result = true;
        }
        return result;
    }

    public List<ProductViewModel> conversionToListViewModel(List<ProductEntity> products) {
        List<ProductViewModel> productsViews = new ArrayList<>();
        for (ProductEntity product : products) {
            ProductViewModel productViewModel = this.mapper.map(product, ProductViewModel.class);
            productsViews.add(productViewModel);
        }
        return productsViews;
    }


}
