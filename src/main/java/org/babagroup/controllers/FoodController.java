package org.babagroup.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.Food;
import org.babagroup.models.Restaurant;
import org.babagroup.repository.FoodRepository;
import org.babagroup.repository.OrderRepository;
import org.babagroup.resreq.FoodDto;
import org.babagroup.resreq.FoodRes;
import org.babagroup.services.RestaurantServices;
import org.babagroup.services.UserServices;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@RequestScoped
@Path("/food")
public class FoodController {
    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    UserServices userServices;

    @Inject
    OrderRepository orderRepository;

    @Inject
    FoodRepository foodRepository;

    @Inject
    RestaurantServices restaurantServices;

    @Path("/get-all")
    @RolesAllowed({"admin", "owner"})
    public Response getAllFood() {
        Restaurant restaurant = restaurantServices.getRestaurant();
        List<Food> foods = foodRepository.findByRestaurantId(restaurant.getId());
        return Response.ok(foods).build();
    }

    @Path("/get-food-by-id")
    @RolesAllowed({"admin", "owner"})
    public Response getFoodById(String id) {
        Food food = foodRepository.findFoodById(id).orElseThrow(() -> new DataError("Data is not found"));
        FoodRes dto = new FoodRes();
        dto.setRestaurantId(food.getId());
        dto.setAvailable(food.isAvailable());
        dto.setName(food.getName());
        dto.setDescription(food.getDescription());
        dto.setImages(food.getImages());
        dto.setPrice(food.getPrice());
        dto.setProfit(food.getProfit());
        dto.setSeasonal(food.isSeasonal());
        dto.setVegetarian(food.isVegetarian());
        return Response.ok(dto).build();
    }
}
