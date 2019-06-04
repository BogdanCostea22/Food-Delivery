package com.sd.assignment2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.opencsv.bean.CsvBindByPosition;
import org.hibernate.engine.internal.Cascade;
import org.hibernate.engine.jdbc.spi.ConnectionObserverAdapter;
import org.springframework.objenesis.ObjenesisSerializer;

import javax.persistence.*;
import java.util.Observable;

@Entity
@Table(name="order")
public class Order extends Observable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @CsvBindByPosition(position = 0)
    private Long orderId;
    @CsvBindByPosition(position = 1)
    private int number;
    @CsvBindByPosition(position = 2)
    private int restaurantId;
    @CsvBindByPosition(position = 3)
    private boolean deliveryStatus;


    @ManyToOne(cascade =  CascadeType.ALL, optional = false)
    @JsonManagedReference
    User user;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JsonManagedReference
    Subcategory subcategory;

    public Order() {
    }

    public Order(int number, int restaurantId, boolean deliveryStatus) {

        this.number = number;
        this.restaurantId = restaurantId;
        this.deliveryStatus = deliveryStatus;
    }

    public Long getId() {
        return orderId;
    }

    public void setId(Long id) {
        this.orderId = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

        this.addObserver(user);
        setChanged();
        String response = "New order was placed to restaurant" + restaurantId;
        notifyObservers(response);
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public boolean isDeliveryStatus() {
        return deliveryStatus;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantid(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(boolean deliveryStatus){
        if(deliveryStatus)
        {
            this.addObserver(user);
            setChanged();
            String response = "Order status delivered";
            notifyObservers(response);
        }
        this.deliveryStatus = deliveryStatus;
    }
}
