package com.udemy.itemservice.Service.Implement;

import com.udemy.itemservice.Model.Item;
import com.udemy.itemservice.Model.Product;
import com.udemy.itemservice.Service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("restTemplateService")
public class ItemImpl implements IItemService {

    @Autowired
    private RestTemplate rest;

    @Override
    public List<Item> findAll() {

        List<Product> products = Arrays.asList(rest.getForObject("http://product-service/list", Product[].class));

        return products.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer quantity) {

        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("id", id.toString());

        Product product = rest.getForObject("http://product-service/detail/{id}", Product.class, pathVariables);

        return new Item(product, quantity);
    }

    @Override
    public Product save(Product product) {

        HttpEntity<Product> body = new HttpEntity<Product>(product);
        ResponseEntity<Product> result = rest.exchange("http://product-service/create", HttpMethod.POST, body, Product.class);

        Product response = result.getBody();

        return response;
    }

    @Override
    public Product update(Product product, Long id) {

        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("id", id.toString());

        HttpEntity<Product> body = new HttpEntity<Product>(product);
        ResponseEntity<Product> response = rest.exchange("http://product-service/edit/{id}", HttpMethod.PUT, body, Product.class, pathVariables);

        return response.getBody();
    }

    @Override
    public void delete(Long id) {

        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("id", id.toString());

        rest.delete("http://product-service/delete/{id}", pathVariables);

    }
}
