package com.example.canteen.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class User {
    private Long id;
    private String username;
    private String password;
    private boolean activate;
    private boolean role;
    private Set<Restaurant> restaurants;

    public User() {
    }

    public User(String username, String password, Restaurant... restaurants) {
        this.username = username;
        this.password = password;
        this.restaurants = Stream.of(restaurants).collect(Collectors.toSet());
        this.restaurants.forEach(x -> x.setAdmin(this));
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        activate = false;
        restaurants = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    //Add new restaurant
    public void addRestaurant(Restaurant restaurant)
    {
        restaurants.add(restaurant);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", activate=" + activate +
                '}';
    }
}
