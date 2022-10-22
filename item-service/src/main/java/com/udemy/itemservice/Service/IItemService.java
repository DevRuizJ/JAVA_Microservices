package com.udemy.itemservice.Service;

import com.example.commonslibrary.Entity.Product;
import com.udemy.itemservice.Model.Item;

import java.util.List;

public interface IItemService {

    public List<Item> findAll();
    public Item findById(Long id, Integer quantity);
    public Product save(Product product);
    public Product update(Product product, Long id);
    public void delete(Long id);
}
