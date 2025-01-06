package org.babagroup.services;

import org.babagroup.models.Food;
import org.babagroup.resreq.FoodDto;
import org.babagroup.resreq.FoodRes;

import java.util.List;

public interface FoodAdminService {
    public FoodRes getFood(String id);
    FoodRes saveFood(Food food);
    FoodRes updateFood(FoodDto req);
    Food mapToObject(FoodDto foodDto);
    FoodRes mapToDto(Food food);
    List<FoodRes> getAllFood();

}
