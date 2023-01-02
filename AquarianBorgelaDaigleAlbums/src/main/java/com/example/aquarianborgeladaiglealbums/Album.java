package com.example.aquarianborgeladaiglealbums;

import java.io.Serializable;

public class Album implements Serializable {
    private String id;
    private String title;
    private String publisher;
    
    public Album() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}