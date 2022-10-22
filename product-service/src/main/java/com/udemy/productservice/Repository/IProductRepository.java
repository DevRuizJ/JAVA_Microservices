package com.udemy.productservice.Repository;


import com.example.commonslibrary.Entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface IProductRepository extends CrudRepository<Product, Long> {
}
