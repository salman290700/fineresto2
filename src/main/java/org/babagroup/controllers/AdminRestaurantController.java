package org.babagroup.controllers;

import io.quarkus.logging.Log;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.*;
import org.babagroup.repository.*;
import org.babagroup.resreq.*;
import org.babagroup.services.CategoryService;
import org.babagroup.services.FoodAdminService;
import org.babagroup.services.RestaurantServices;
import org.babagroup.services.UserServices;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Path("/admin-restaurant")
public class AdminRestaurantController {

    @Inject
    RestaurantServices restaurantServices;

    @Inject
    UserRepository userRepository;

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    RestaurantRepository restaurantRepository;

    @Inject
    AddressRepository addressRepository;


    @Inject
    FoodAdminService foodAdminService;

    @Inject
    UserServices userServices;

    @Inject
    FoodRepository foodRepository;

    @Inject
    CategoryService categoryService;

    @Inject
    CategoryRepository categoryRepository;


    String user_not_found = "User not found";
    String user_error = "Please input the right email and password";
    String Email_error = "Email not found";
    String user_error_exists = "Email is already used by another account";
    String otp_exp = "Your Otp is Expired";
    String otp_false = "False Otp";
    String restaurant_not_found = "restaurant not found";

    @Path("/create-restaurant")
    @POST
    @RolesAllowed({"admin", "owner"})
    public Response createRestaurant(CreateRestaurantReq req) {
        User user = userRepository.findByEmail(jsonWebToken.getSubject()).orElseThrow(() -> new DataError(user_not_found));

        Restaurant restaurant = new Restaurant();
        restaurant.setName(req.getRestaurant_name());
        restaurant.setDescription(req.getRestaurant_desc());
        List<User> users = new ArrayList<>();
        users.add(user);
        restaurant.setAdmin(users);
        restaurant.setOwner(user);
        restaurant.setCreatedAt(new Date());
        restaurant.setUpdatedAt(new Date());
        restaurant.setCreatedBy(user.getId());
        restaurant.setUpdatedBy(user.getId());
//        Set CuisineType
        restaurant.setCuisineType(req.getCuisine_type());
//        Set address
        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setUpdatedAt(new Date());
        address.setCreatedAt(new Date());
        address.setCreatedBy(user.getId());
        address.setUpdatedBy(user.getId());
        address.setCountry(req.getCountry());
        address.setState(req.getState());
        address.setStreet(req.getStreet());
        address.setCity(req.getCity());

        Address savedAddress = addressRepository.save(address);
        restaurant.setAddress(savedAddress);
//        Set ContactInformation
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setInstagram(req.getInstagram());
        contactInformation.setMobile(req.getMobile());
        contactInformation.setTwitter(req.getTwitter());
        restaurant.setContactInformation(contactInformation);
        restaurant.setFoods(new ArrayList<>());
        restaurant.setImages(new ArrayList<>());
        restaurant.setOpened(false);
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOpeningHours(req.getOpening_hours());
        restaurant.setId(UUID.randomUUID().toString());
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        user.setRestaurant(savedRestaurant);
        if(!restaurant.getAdmin().isEmpty()) {
            restaurant.getAdmin().add(user);
        }
        List<User> admins = new ArrayList<>();
        admins.add(user);
        restaurant.setAdmin(admins);
        userRepository.save(user);
        return Response.ok(restaurantServices.mapToDto(restaurant)).build();
    }

    @Path("/get-restaurant-by-id")
    @GET
    @RolesAllowed({"admin", "owner"})
    public Response getRestaurantById() {
        RestaurantResponse response = restaurantServices.getRestaurantResponse();
        return Response.ok(response).build();
    }

    @Path("/update-restaurant")
    @PUT
    @RolesAllowed({"admin", "owner"})
    public Response updateRestaurant(RestaurantReq req) {
        String user_id = userServices.getUserByEmail().getId();
        Restaurant restaurant = restaurantServices.getRestaurant();
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setOpened(req.isOpened());
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        Address address = addressRepository.findById(req.getAddress().getId()).orElseThrow(() -> new DataError("Data is not found"));
        address.setCity(req.getAddress().getCity());
        address.setStreet(req.getAddress().getStreet());
        address.setState(req.getAddress().getState());
        address.setCountry(req.getAddress().getCountry());
        address.setUpdatedBy(user_id);
        address.setCreatedAt(new Date());
        Address savedAddress = addressRepository.save(address);
        restaurant.setAddress(savedAddress);
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setContactInformation(req.getContactInformation());
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return Response.ok(restaurantServices.mapToDto(savedRestaurant)).build();
    }

    @Path("/close-restaurant")
    @PUT
    @RolesAllowed({"admin", "owner"})
    public Response closeRestaurant(RestaurantReq req) {
        Restaurant restaurant = restaurantServices.getRestaurant();
        restaurant.setOpened(false);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        RestaurantResponse restaurantDto = restaurantServices.mapToDto(savedRestaurant);
        return Response.ok(restaurantDto).build();
    }

    @Path("/delete-restaurant")
    @DELETE
    @RolesAllowed({"owner"})
    public Response deleteRestaurant() {
        restaurantRepository.deleteById(
                userRepository.findByEmail(jsonWebToken.getSubject()).orElseThrow(() -> new DataError("User not found")).getRestaurant().getId()
        );
        Restaurant deletedRestaurant = restaurantServices.getRestaurantByAdminId(
                userRepository.findByEmail(jsonWebToken.getSubject()).orElseThrow(() -> new DataError("User not found")).getRestaurant().getId());
        if(deletedRestaurant!=null) {
            return Response.ok("Restaurant Deletion Failed").build();
        }
        return Response.noContent().build();
    }

    @Path("/create-food")
    @POST
    @RolesAllowed({"admin", "owner"})
    public Response createFood(FoodDto req) {
//        mapToObject
        Food food = new Food();
        food.setId(UUID.randomUUID().toString());
        User user = userServices.getUserByEmail();
        Restaurant restaurant = restaurantRepository.findById(user.getRestaurant().getId()).orElseThrow(() -> new DataError("Restaurant not found"));
        //            Saving hacker data
        if(!restaurant.getId().equals(user.getRestaurant().getId())) {
            return Response.noContent().build();
        }
        food.setRestaurant(restaurant);
        food.setName(req.getName());
        food.setDescription(req.getDescription());
        food.setAvailable(true);
        food.setVegetarian(req.isVegetarian());
        food.setProfit(req.getProfit());
        food.setPrice(req.getPrice());
        food.setSeasonal(false);
        food.setImages(req.getImages());
        food.setUpdatedAt(new Date());
        food.setCreatedBy(user.getId());
        food.setUpdatedBy(user.getId());
        food.setCreatedAt(new Date());
//        Handle Category
        Category newCategory = new Category();
        newCategory.setId(UUID.randomUUID().toString());
        newCategory.setCreatedBy(user.getId());
        newCategory.setUpdatedBy(user.getId());
        newCategory.setCreatedAt(new Date());
        newCategory.setUpdatedAt(new Date());
        newCategory.setName(req.getFoodCategory().getName());
        Category category = categoryRepository.findByName(req.getFoodCategory().getName()).orElse(categoryRepository.save(newCategory));
        food.setFoodCategory(category);
        Log.info("catgeory name " + food.getFoodCategory().getName());
        food.setAvailable(true);
//        Food food = foodAdminService.mapToObject(req);
        Food savedFood = foodRepository.save(food);
        FoodRes dto = new FoodRes();
        dto.setRestaurantId(savedFood.getId());
        dto.setAvailable(savedFood.isAvailable());
        dto.setName(savedFood.getName());
        dto.setDescription(savedFood.getDescription());
        dto.setImages(savedFood.getImages());
        dto.setPrice(savedFood.getPrice());
        dto.setProfit(savedFood.getProfit());
        dto.setSeasonal(savedFood.isSeasonal());
        dto.setVegetarian(savedFood.isVegetarian());
//        CategoryDto handler
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(savedFood.getFoodCategory().getName());
        dto.setFoodCategory(categoryDto);
        return Response.ok(dto).build();
    }

    public Category makeCategory(FoodDto dto, User user) {
        Category newCategory = new Category();
        newCategory.setId(UUID.randomUUID().toString());
        newCategory.setCreatedBy(user.getId());
        newCategory.setUpdatedBy(user.getId());
        newCategory.setCreatedAt(new Date());
        newCategory.setUpdatedAt(new Date());
        newCategory.setName(dto.getFoodCategory().getName());
        Category savedCate =categoryRepository.save(newCategory);
        return savedCate;
    }

    @Path("/update-food")
    @POST
    @RolesAllowed({"admin", "owner"})
    public Response updateFood(FoodDto req) {
        //        mapToObject
        Food food = foodRepository.findFoodById(req.getId()).orElseThrow(() -> new DataError("Food is not found"));
        User user = userServices.getUserByEmail();
        if(!food.getRestaurant().getId().equals(user.getRestaurant().getId())) {
//            Saving hacker data
            return Response.noContent().build();
        }
        Restaurant restaurant = restaurantRepository.findById(user.getRestaurant().getId()).orElseThrow(() -> new DataError("Restaurant not found"));
        food.setRestaurant(restaurant);
        Log.info("name " + req.getName());
        food.setName(req.getName());
        food.setDescription(req.getDescription());
        Log.info("name " + req.getDescription());
        food.setAvailable(true);
        food.setVegetarian(req.isVegetarian());
        food.setProfit(req.getProfit());
        food.setPrice(req.getPrice());
        food.setSeasonal(false);
        food.setImages(req.getImages());
        food.setUpdatedAt(new Date());
        food.setCreatedBy(user.getId());
        food.setUpdatedBy(user.getId());
        food.setCreatedAt(new Date());
//        Handle Category
        Category newCategory = new Category();
        newCategory.setId(UUID.randomUUID().toString());
        newCategory.setCreatedBy(user.getId());
        newCategory.setUpdatedBy(user.getId());
        newCategory.setCreatedAt(new Date());
        newCategory.setUpdatedAt(new Date());
        newCategory.setName(req.getFoodCategory().getName());
        Category findCategory = categoryRepository.findByName(req.getFoodCategory().getName()).orElse(null);
        Log.info(findCategory.getName());
        Category category = categoryRepository.findByName(req.getFoodCategory().getName()).orElse(null);
        if(category.equals(null)) {
            category = categoryRepository.save(newCategory);
        }
        food.setFoodCategory(category);
        Log.info("catgeory name " + food.getFoodCategory().getName());
        food.setAvailable(true);
//        Food food = foodAdminService.mapToObject(req);
        Food savedFood = foodRepository.save(food);
        FoodRes dto = new FoodRes();
        dto.setRestaurantId(savedFood.getId());
        dto.setAvailable(savedFood.isAvailable());
        dto.setName(savedFood.getName());
        dto.setDescription(savedFood.getDescription());
        dto.setImages(savedFood.getImages());
        dto.setPrice(savedFood.getPrice());
        dto.setProfit(savedFood.getProfit());
        dto.setSeasonal(savedFood.isSeasonal());
        dto.setVegetarian(savedFood.isVegetarian());
//        CategoryDto handler
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(savedFood.getFoodCategory().getName());
        dto.setFoodCategory(categoryDto);
        return Response.ok(dto).build();
    }

    @Path("/delete-food")
    @DELETE
    @RolesAllowed({"admin", "owner"})
    public Response deleteFood(String foodId) {
        foodRepository.deleteById(foodId);
        return Response.ok("Your food has been deleted").build();
    }

    @Path("/get-all")
    @RolesAllowed({"admin", "owner"})
    @GET
    public Response getAllFood() {
        Restaurant restaurant = restaurantServices.getRestaurant();
        List<Food> foods = foodRepository.findByRestaurantId(restaurant.getId());
        if(foods.isEmpty()) {
            return Response.ok().build();
        }
        List<FoodRes> foodResList = new ArrayList<>();
        for(Food item: foods) {
            FoodRes foodRes = new FoodRes();
            foodRes.setName(item.getName());
            foodRes.setDescription(item.getDescription());
            foodRes.setImages(item.getImages());
            foodRes.setProfit(item.getProfit());
            foodRes.setPrice(item.getPrice());
//            Category Dto
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(item.getFoodCategory().getName());
            foodRes.setFoodCategory(categoryDto);
            foodRes.setVegetarian(item.isVegetarian());
            foodRes.setSeasonal(item.isSeasonal());
            foodRes.setAvailable(item.isAvailable());
            foodResList.add(foodRes);
        }
        return Response.ok(foodResList).build();
    }

    @Path("/get-food-by-id")
    @RolesAllowed({"admin", "owner"})
    @POST
    public Response getFoodById(FoodIdReq req) {
        Food food = foodRepository.findFoodById(req.getId()).orElseThrow(() -> new DataError("Data is not found"));
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
        dto.setAvailable(food.isAvailable());
//        Category Dto
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(food.getFoodCategory().getName());
        dto.setFoodCategory(categoryDto);
        return Response.ok(dto).build();
    }
}