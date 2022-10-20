package com.udemy.itemservice.Service;

import com.udemy.itemservice.Model.Item;
import com.udemy.itemservice.Model.Product;

import java.util.List;

public interface IItemService {

    public List<Item> findAll();
    public Item findById(Long id, Integer quantity);
    public Product save(Product product);
    public Product update(Product product, Long id);
    public void delete(Long id);
}
