package com.udemy.itemservice.Client;

import com.example.commonslibrary.Entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service"/*, url = "${product-service.ribbon.listOfServers}"*/)
public interface ProductRestClient {

    @GetMapping
    public List<Product> list();
    @GetMapping("/detail/{id}")
    public Product detail(@PathVariable Long id);
    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product);
    @PutMapping("/edit/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable Long id);
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id);

}
