package org.babagroup.services.implementations;
import jakarta.inject.Inject;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.Address;
import org.babagroup.models.Restaurant;
import org.babagroup.models.User;
import org.babagroup.repository.RestaurantRepository;
import org.babagroup.resreq.AddressDto;
import org.babagroup.resreq.RestaurantReq;
import org.babagroup.resreq.RestaurantResponse;
import org.babagroup.services.RestaurantServices;
import org.babagroup.services.UserServices;
import org.babagroup.utils.AddressUtils;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServicesImpl implements RestaurantServices {
    @Inject
    UserServices userServices;

    @Inject
    RestaurantRepository restaurantRepository;

    AddressUtils addressUtils = new AddressUtils();

    String restaurant_not_found = "restaurant not found";

    @Override
    public Restaurant getRestaurantByAdminId(String admin_id) {
        return null;
    }

    @Override
    public RestaurantResponse getRestaurantResponse() {
        Restaurant restaurant = restaurantRepository.findById(userServices.getUserByEmail().getRestaurant().getId()).orElseThrow(() -> new DataError("Restaurant is not found"));
        return  mapToDto(restaurant);
    }

    @Override
    public Restaurant getRestaurant() {
        User user = userServices.getUserByEmail();
        return restaurantRepository.findById(user.getRestaurant().getId()).orElseThrow(() -> new DataError(restaurant_not_found));
    }

    @Override
    public RestaurantResponse updateRestaurant(RestaurantReq req) {
        Restaurant restaurant = getRestaurant();
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setOpened(req.isOpened());
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());

        restaurant.setAddress(addressUtils.mapToObject(req.getAddress()));
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setContactInformation(req.getContactInformation());
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return mapToDto(savedRestaurant);
    }

    @Override
    public RestaurantResponse closeRestaurant(RestaurantReq req) {
        Restaurant restaurant = getRestaurant();
        restaurant.setOpened(false);
        return mapToDto(restaurant);
    }

    @Override
    public boolean checkedAdmin() {
        return false;
    }

    @Override
    public RestaurantResponse mapToDto(Restaurant restaurant) {
        return  new RestaurantResponse(restaurant.getName(), restaurant.getDescription(), restaurant.getCuisineType(), addressUtils.mapToDto(restaurant.getAddress()), restaurant.getContactInformation(), restaurant.getOpeningHours(), restaurant.getImages(), restaurant.isOpened());
    }
}
