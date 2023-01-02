package com.example.aquarianborgeladaiglealbums;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
//import javax.servlet.ServletContext;
//import javax.ws.rs.core.Context;
////import javax.ws.rs.core.UriInfo;
//import javax.ws.rs.Produces;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/albums")
public class AlbumService {


    @Autowired
    ServletContext scontext;

    private final ConcurrentHashMap<String, Album> map = new ConcurrentHashMap<>();
    private String dbfile;

    @RequestMapping(value = "/path-test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPath() {
        ensureDBIsLoaded();
        return dbfile;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/{id:\\d+}")
    public ResponseEntity<Album> getAlbum(@PathVariable("id") String id) {
        ensureDBIsLoaded();
        if(!map.containsKey(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(map.get(id));
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RequestMapping(value = "/db-status")
    public String status() {
        try {
            loadDB();
            return String.format("DB Loaded with %d records", map.size());
        }
        catch(Exception e) {
            return e.getMessage();
        }
    }
    
    private void ensureDBIsLoaded() {
        try {
            loadDB();
        }
        catch(Exception e) {
            map.clear();
        }
    }
    
    private void loadDB() {
        if(map.isEmpty()) {
            dbfile = scontext.getRealPath("target/classes/albums.txt");
            try(BufferedReader r = new BufferedReader(new FileReader("target/classes/albums.txt"))) {
                for(String s = r.readLine(); s != null; s = r.readLine()) {
                    String[] fields = s.split(",");
                    Album a = new Album();
                    a.setId(fields[0]);
                    a.setTitle(fields[1]);
                    a.setPublisher(fields[3]);
                    map.put(a.getId(), a);
                }
            }
            catch(IOException e) {
                map.clear();
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
