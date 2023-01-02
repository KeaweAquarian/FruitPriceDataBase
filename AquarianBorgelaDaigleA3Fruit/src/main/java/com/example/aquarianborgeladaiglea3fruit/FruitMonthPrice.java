package com.example.aquarianborgeladaiglea3fruit;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/fruit-month-price")
public class FruitMonthPrice {

        @Autowired
        ServletContext scontext;

        private final ConcurrentHashMap<String, Fruit> map = new ConcurrentHashMap<>();



        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @RequestMapping(value = "/fruit/{fruit}/month/{month}")
        public ResponseEntity<Fruit> getAlbum(@PathVariable("fruit") String fruit, @PathVariable("month") String month) {
            ensureDBIsLoaded(fruit, month);
            if(!map.containsKey(fruit))
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok().body(map.get(fruit));
        }


        private void ensureDBIsLoaded(String fruit, String month) {
            try {
                loadDB(fruit, month);
            }
            catch(Exception e) {
                map.clear();
            }
        }

        private void loadDB(String fruit, String month) {

            int monthValue = 1;
            switch (month){
                case "jan":
                    monthValue = 1;
                    break;
                case "feb":
                    monthValue = 2;
                    break;
                case "mar":
                    monthValue = 3;
                    break;
                case "apr":
                    monthValue = 4;
                    break;
                case "may":
                    monthValue = 5;
                    break;
                case "jun":
                    monthValue = 6;
                    break;
                case "jul":
                    monthValue = 7;
                    break;
                case "aug":
                    monthValue = 8;
                    break;
                case "sep":
                    monthValue = 9;
                    break;
                case "oct":
                    monthValue = 10;
                    break;
                case "nov":
                    monthValue = 11;
                    break;
                case "dec":
                    monthValue = 12;
                    break;
                    default:
                    // code block
            }

                try(BufferedReader r = new BufferedReader(new FileReader("target/classes/fruit.csv"))) {
                    int counter = -1;
                    for(String s = r.readLine(); s != null; s = r.readLine()) {
                        counter++;
                        String[] fields = s.split(",");
                        if (fields[0].toLowerCase().equals(fruit)){
                            Fruit f = new Fruit();
                            f.setId(9998 + counter);
                            f.setFruit(fruit);
                            f.setMonth(month);
                            f.setFmp(Double.parseDouble(fields[monthValue]));
                            f.setEnvironment("8082");
                            map.put(f.getFruit(), f);

                            break;
                        }

                    }
                }
                catch(IOException e) {
                    map.clear();
                    throw new RuntimeException(e.getMessage());
                }

        }
    }
