package com.example.aquarianborgeladaiglea3totalprice;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/fruit-total-price")
public class FruitTotalPrice {

    @Autowired
    ServletContext scontext;

    @Autowired
    private RestTemplate restTemplate;

    private final ConcurrentHashMap<String, Fruits> map = new ConcurrentHashMap<>();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/fruit/{fruit}/month/{month}/quantity/{quantity:\\d+}")
    public ResponseEntity<Fruits> getFruits(@PathVariable("fruit") String fruit, @PathVariable("month") String month,
                                            @PathVariable("quantity") int quantity) {

        String url = "http://FRUITMONTHPRICE/fruit-month-price/fruit/" + fruit + "/month/" + month;

        Fruit fruits = restTemplate.getForObject(url, Fruit.class);


        ensureDBIsLoaded(fruits, quantity);
        if(!map.containsKey(fruit))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(map.get(fruit));
    }



    private void ensureDBIsLoaded(Fruit fruit, int quantity) {
        try {
            loadDB(fruit, quantity);
        }
        catch(Exception e) {
            map.clear();
        }
    }

    private void loadDB(Fruit fruit, int quantity) {
        DecimalFormat df=new DecimalFormat("#.##");

        try {
                    Fruits f = new Fruits();
                    f.setId(fruit.getId());
                    f.setFruit(fruit.getFruit());
                    f.setMonth(fruit.getMonth());
                    f.setFmp(fruit.getFmp());
                    f.setQuantity(quantity);
                    f.setTotalPrice(Double.parseDouble(df.format(((quantity) * fruit.getFmp()))));
                    f.setEnvironment("8084");
                    map.put(f.getFruit(), f);

        }
        catch(Exception e) {
            map.clear();
            throw new RuntimeException(e.getMessage());
        }
//            }

    }
}
