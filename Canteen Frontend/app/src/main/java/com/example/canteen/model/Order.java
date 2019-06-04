package com.example.canteen.model;


public class Order {
    private Long orderId;
    private int number;
    private int restaurantId;
    private boolean deliveryStatus;



    User user;
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

    public void setDeliveryStatus(boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "\n \t orderId=" + orderId +
                "\n,\t number=" + number +
                "\n,\t restaurantId=" + restaurantId +
                "\n,\t deliveryStatus=" + deliveryStatus +
                "\n,\t user={" + user.toString() +
                "}}\n";
    }
}
