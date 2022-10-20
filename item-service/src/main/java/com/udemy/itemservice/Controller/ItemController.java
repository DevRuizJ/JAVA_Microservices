package com.udemy.itemservice.Controller;

import com.udemy.itemservice.Model.Item;
import com.udemy.itemservice.Model.Product;
import com.udemy.itemservice.Service.IItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RefreshScope
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    @Qualifier("feignService")
    //@Qualifier("restTemplateService")
    private IItemService serv;

    @Value("${configuration.text}")
    private String configText;

    @Autowired
    private Environment env;

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

    @CircuitBreaker(name = "items", fallbackMethod = "alternativeMethod")
    @GetMapping("/detail2/{id}/quantity/{quantity}")
    public Item detail2(@PathVariable Long id, @PathVariable Integer quantity){
        return serv.findById(id, quantity);
    }

    @CircuitBreaker(name = "items", fallbackMethod = "alternativeMethod2")
    @TimeLimiter(name = "items")
    @GetMapping("/detail3/{id}/quantity/{quantity}")
    public CompletableFuture<Item> detail3(@PathVariable Long id, @PathVariable Integer quantity){
        return CompletableFuture.supplyAsync(() -> serv.findById(id, quantity));
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

    public CompletableFuture<Item> alternativeMethod2(Long id, Integer quantity, Throwable e)
    {
        log.info(e.getMessage());

        Item item = new Item();

        Product product = new Product();

        item.setQuantity(quantity);
        product.setId(id);
        product.setName("SOny");
        product.setPrice(500.545);
        item.setProduct(product);

        return CompletableFuture.supplyAsync(() -> item);
    }

    @GetMapping("/get-config")
    public ResponseEntity<?> getCOnfiguration(@Value("${server.port}") String port){

        log.info(configText);

        Map<String, String> json = new HashMap<>();
        json.put("configText", configText);
        json.put("port", port);

        if (env.getActiveProfiles().length> 0 && env.getActiveProfiles()[0].equals("dev")){
            json.put("autor.name", env.getProperty("configuration.author.name"));
            json.put("autor.email", env.getProperty("configuration.author.email"));
        }

        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product){
        return serv.save(product);
    }

    @PutMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product editProduct(@RequestBody Product product, @PathVariable Long id){
        return serv.update(product, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id){
        serv.delete(id);
    }
}
