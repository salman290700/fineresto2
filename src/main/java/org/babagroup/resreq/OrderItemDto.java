package org.babagroup.resreq;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

@RegisterForReflection
public class OrderItemDto {
    private String id;
    private String foodName;

    private int quantity;

    private Long price;

    private Long totalPrice;

    private String username;

    private List<String> image_photo;

    public OrderItemDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderItemDto(String id, String foodName, int quantity, Long price, Long totalPrice, String username, List<String> image_photo) {
        this.id = id;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.username = username;
        this.image_photo = image_photo;
    }

    public List<String> getImage_photo() {
        return image_photo;
    }

    public void setImage_photo(List<String> image_photo) {
        this.image_photo = image_photo;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
