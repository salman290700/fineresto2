package org.babagroup.utils;

import io.quarkus.logging.Log;
import org.babagroup.models.Restaurant;
import org.babagroup.resreq.RestaurantResponse;

public class RestaurantUtils  {
    private Restaurant restaurant;

    AddressUtils addressUtils = new AddressUtils();

    public RestaurantUtils(Restaurant restaurant) {
        this.restaurant = restaurant;
        Log.info("Restaurant utils constructor" + this.restaurant.getAddress().getCity().toString());
    }

    public RestaurantResponse mapToDto(Restaurant restaurant) {
        if(restaurant.equals(null)) {
            return null;
        }
        Log.info("Restaurant utils mapToDto" + this.restaurant.getAddress().getCity().toString());
        RestaurantResponse response = new RestaurantResponse(restaurant.getName(), restaurant.getDescription(), restaurant.getCuisineType(), addressUtils.mapToDto(restaurant.getAddress()), restaurant.getContactInformation(), restaurant.getOpeningHours(), restaurant.getImages(), restaurant.isOpened());
        return response;
    }


}
