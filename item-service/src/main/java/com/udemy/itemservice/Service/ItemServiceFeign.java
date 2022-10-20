package com.udemy.itemservice.Service;

import com.udemy.itemservice.Client.ProductRestClient;
import com.udemy.itemservice.Model.Item;
import com.udemy.itemservice.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("feignService")
//@Primary
public class ItemServiceFeign implements IItemService{

    @Autowired
    private ProductRestClient feignClient;

    @Override
    public List<Item> findAll() {
        return feignClient.list().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer quantity) {
        return new Item(feignClient.detail(id), quantity);
    }

    @Override
    public Product save(Product product) {
        return feignClient.createProduct(product);
    }

    @Override
    public Product update(Product product, Long id) {
        return feignClient.updateProduct(product, id);
    }

    @Override
    public void delete(Long id) {
        feignClient.deleteProduct(id);
    }
}
