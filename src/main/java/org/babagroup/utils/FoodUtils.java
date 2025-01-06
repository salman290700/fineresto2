package org.babagroup.utils;

import jakarta.inject.Inject;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.Category;
import org.babagroup.models.Food;
import org.babagroup.models.Restaurant;
import org.babagroup.models.User;
import org.babagroup.repository.CategoryRepository;
import org.babagroup.repository.RestaurantRepository;
import org.babagroup.resreq.FoodDto;
import org.babagroup.resreq.FoodRes;
import org.babagroup.services.CategoryService;
import org.babagroup.services.UserServices;

import java.util.Date;

public class FoodUtils {

    @Inject
    RestaurantRepository restaurantRepository;

    @Inject
    CategoryService categoryService;
    @Inject
    UserServices userService;

    @Inject
    CategoryRepository categoryRepository;
    public Food mapToObject(FoodDto foodDto) {
        Food food = new Food();
        food.setAvailable(foodDto.isAvailable());
//        Needs Catgory Utils
        Category findCategory = categoryRepository.findByName(food.getFoodCategory().getName()).orElseThrow(() -> new DataError("Food category is not found"));
        Category category = findCategory;
        food.setFoodCategory(category);
        food.setName(foodDto.getName());
        food.setDescription(foodDto.getDescription());
        food.setImages(foodDto.getImages());
        food.setCreatedAt(new Date());
        food.setUpdatedAt(new Date());
        User user = userService.getUserByEmail();
        food.setCreatedBy(user.getId());
        food.setUpdatedBy(user.getId());
        food.setPrice(foodDto.getPrice());
        food.setProfit(foodDto.getProfit());
        food.setSeasonal(foodDto.isSeasonal());
        food.setVegetarian(foodDto.isVegetarian());
        Restaurant restaurant = restaurantRepository.findById(user.getRestaurant().getId()).orElseThrow(() -> new DataError("Restaurant is not found"));
        food.setRestaurant(restaurant);
        return food;
    }

    public FoodRes mapToDto(Food food) {
        FoodRes res = new FoodRes();
        res.setAvailable(food.isAvailable());
//        Needs Catgory Utils
        res.setName(food.getName());
        res.setDescription(food.getDescription());
        res.setImages(food.getImages());
        res.setPrice(food.getPrice());
        res.setProfit(food.getProfit());
        res.setSeasonal(food.isSeasonal());
        res.setVegetarian(food.isVegetarian());
        res.setRestaurantId(food.getRestaurant().getId());
        return res;
    }
}
