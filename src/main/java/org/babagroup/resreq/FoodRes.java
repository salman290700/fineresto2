package org.babagroup.resreq;

import org.babagroup.models.Category;

import java.util.ArrayList;
import java.util.List;

public class FoodRes {
    private String name;

    private String description;

    private Long price;

    private CategoryDto foodCategory;

    private List<String> images;

    private boolean available;

    private String restaurantId;

    private boolean isVegetarian;

    private boolean isSeasonal;

    private Long profit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public CategoryDto getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(CategoryDto foodCategory) {
        this.foodCategory = foodCategory;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public boolean isSeasonal() {
        return isSeasonal;
    }

    public void setSeasonal(boolean seasonal) {
        isSeasonal = seasonal;
    }


    public Long getProfit() {
        return profit;
    }

    public void setProfit(Long profit) {
        this.profit = profit;


    }
}
