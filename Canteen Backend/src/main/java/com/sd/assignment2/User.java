package com.sd.assignment2;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.ColumnDefault;


import javax.persistence.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class User implements Observer {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password", unique = true)
    private String password;
    @ColumnDefault("false")
    private boolean activate;
    @ColumnDefault("false")
    private boolean role;
    @OneToMany (cascade = CascadeType.ALL)
    @JsonManagedReference
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
    public void update(Observable o, Object arg) {

        try {
            SocketUtil.sendData((String) arg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println((String) arg);
    }


}
