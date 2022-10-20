package com.udemy.productservice.Controller;

import com.udemy.productservice.Entity.Product;
import com.udemy.productservice.Service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private Environment env;

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private IProductService serv;

    @GetMapping("/list")
    public List<Product> list(){

        return serv.findALl().stream().map(product -> {

            product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            //product.setPort(port);

            return product;
        }).collect(Collectors.toList());

    }

    @GetMapping("/detail/{id}")
    public Product detail(@PathVariable Long id) throws InterruptedException {

        if (id.equals(10L)){
            throw new IllegalStateException("PRODUCTO NO ENCONTRADO!!!!!!!!!!!!!!");
        }

        if (id.equals(7L)){
            TimeUnit.SECONDS.sleep(5L);
        }

        Product product = serv.findById(id);
        product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        //product.setPort(port);
/*
        boolean ok = false;

        if (!ok)
            throw new RuntimeException("Can´t charge to product");
*/
        /*
        try {
            Thread.sleep(2000L);
        }
        catch (InterruptedException ie){
            ie.printStackTrace();
        }
*/
        return product;

    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product){
        return serv.save(product);
    }

    @PutMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product editProduct(@PathVariable Product product, @PathVariable Long id){

        Product productDB = serv.findById(id);

        productDB.setName(product.getName());
        productDB.setPrice(product.getPrice());

        return serv.save(productDB);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id){
        serv.deleteById(id);
    }

}
