package org.babagroup.services;

import org.babagroup.models.Restaurant;
import org.babagroup.resreq.RestaurantReq;
import org.babagroup.resreq.RestaurantResponse;

public interface RestaurantServices {
    public Restaurant getRestaurantByAdminId(String admin_id);
    public RestaurantResponse getRestaurantResponse();
    public Restaurant getRestaurant();
    RestaurantResponse updateRestaurant(RestaurantReq req);
    RestaurantResponse closeRestaurant(RestaurantReq req);
    boolean checkedAdmin();
    RestaurantResponse mapToDto(Restaurant restaurant);
}
