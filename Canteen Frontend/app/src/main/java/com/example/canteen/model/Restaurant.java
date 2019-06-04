package com.example.canteen.model;


import java.util.HashSet;
import java.util.Set;

public class Restaurant {
    private Long id;
    private String name;
    private String location;
    private String phone;
    private String description;
    private User user = new User();


    public Restaurant() {
    }

    public Restaurant(String name, String location, String phone, String description) {
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAdmin(User user)
    {
        this.user = user;
    }

}
