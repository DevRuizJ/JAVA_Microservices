package com.udemy.itemservice.Controller;

import com.udemy.itemservice.Model.Item;
import com.udemy.itemservice.Model.Product;
import com.udemy.itemservice.Service.IItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    @Qualifier("feignService")
    //@Qualifier("restTemplateService")
    private IItemService serv;

    @GetMapping("/list")
    public List<Item> list(@RequestParam(name="nombre") String nombre, @RequestHeader(name="token-request", required = false) String token){

        System.out.println(nombre);
        System.out.println(token);
        return serv.findAll();
    }

    @GetMapping("/detail/{id}/quantity/{quantity}")
    public Item detail(@PathVariable Long id, @PathVariable Integer quantity){
        return cbFactory.create("items")
                .run(() -> serv.findById(id, quantity), e -> alternativeMethod(id, quantity, e));
    }

    public Item alternativeMethod(Long id, Integer quantity, Throwable e)
    {
        log.info(e.getMessage());

        Item item = new Item();

        Product product = new Product();

         item.setQuantity(quantity);
         product.setId(id);
         product.setName("SOny");
         product.setPrice(500.545);
         item.setProduct(product);

         return item;
    }
}
