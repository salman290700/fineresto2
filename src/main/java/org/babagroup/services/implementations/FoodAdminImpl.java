package org.babagroup.services.implementations;

import jakarta.inject.Inject;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.Category;
import org.babagroup.models.Food;
import org.babagroup.models.Restaurant;
import org.babagroup.repository.CategoryRepository;
import org.babagroup.repository.FoodRepository;
import org.babagroup.repository.RestaurantRepository;
import org.babagroup.resreq.FoodDto;
import org.babagroup.resreq.FoodRes;
import org.babagroup.services.CategoryService;
import org.babagroup.services.FoodAdminService;
import org.babagroup.services.UserServices;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class FoodAdminImpl implements FoodAdminService {
    @Inject
    UserServices userService;

    @Inject
    CategoryService categoryService;

    @Inject
    FoodRepository foodRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    RestaurantRepository restaurantRepository;

    String food_404 = "Your looking food is not found";


    @Override
    public FoodRes getFood(String id) {
        String resto_id = userService.getUserByEmail().getRestaurant().getId();

        Food food = foodRepository.findFoodById(id).orElseThrow(() -> new DataError(food_404));
        if(resto_id != food.getRestaurant().getId()) {
            return null;
        }
        return mapToDto(food);
    }

    @Override
    public FoodRes saveFood(Food food) {
        Food savedFood = foodRepository.save(food);
        return mapToDto(savedFood);
    }

    @Override
    public FoodRes updateFood(FoodDto req) {
        Food updatedFood = foodRepository.findFoodById(req.getId()).orElseThrow(() -> new DataError(food_404));
        return mapToDto(updatedFood);
    }

    @Override
    public Food mapToObject(FoodDto foodDto) {
        Food food = new Food();
        food.setAvailable(foodDto.isAvailable());
        if(foodDto.getId() == null) {
            food.setId(UUID.randomUUID().toString());
        }else {
            food.setId(foodDto.getId());
        }
//        Needs Catgory Utils
//        Category
        Category findCategory = categoryRepository.findByName(food.getFoodCategory().getName()).orElseThrow(() -> new DataError("Food category is not found"));
        Category category = findCategory;
        food.setFoodCategory(category);
        food.setName(foodDto.getName());
        food.setDescription(foodDto.getDescription());
        food.setImages(foodDto.getImages());
        food.setCreatedAt(new Date());
        food.setUpdatedAt(new Date());
        food.setCreatedBy(userService.getUserByEmail().getId());
        food.setUpdatedBy(userService.getUserByEmail().getId());
        food.setPrice(foodDto.getPrice());
        food.setProfit(foodDto.getProfit());
        food.setSeasonal(foodDto.isSeasonal());
        food.setVegetarian(foodDto.isVegetarian());
//        Restaurant
        Restaurant restaurant = restaurantRepository.findById(userService.getUserByEmail().getRestaurant().getId()).orElseThrow(() -> new DataError("Restaurant is not found"));
        food.setRestaurant(restaurant);
        return food;
    }

    @Override
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

    @Override
    public List<FoodRes> getAllFood() {
//        List<Food> = foodRepository
        return List.of();
    }
}
