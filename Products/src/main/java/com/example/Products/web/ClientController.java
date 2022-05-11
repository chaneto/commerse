package com.example.Products.web;

import com.example.Products.model.views.ProductRestViewModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("clients")
public class ClientController {

    @GetMapping
    @ApiOperation(httpMethod = "GET", value = "Get all clients", response = ProductRestViewModel.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public String getClient() {
        String uri = "http://localhost:8000/products?orderBy=name&direction=DESC&page=1&pageSize=3";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }
}
