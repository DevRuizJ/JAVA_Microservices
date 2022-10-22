package com.udemy.productservice.Service;


import com.example.commonslibrary.Entity.Product;

import java.util.List;

public interface IProductService {

    public List<Product> findALl();
    public Product findById(Long id);
    public Product save(Product product);
    public void deleteById(Long id);

}
