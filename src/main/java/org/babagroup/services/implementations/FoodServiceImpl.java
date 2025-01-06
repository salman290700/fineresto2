package org.babagroup.services.implementations;

import jakarta.inject.Inject;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.Food;
import org.babagroup.repository.FoodRepository;
import org.babagroup.resreq.FoodRes;
import org.babagroup.services.FoodService;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl implements FoodService {
    @Inject
    FoodRepository foodRepository;
    @Override
    public Food getFoodById(String name) {
        Food food = foodRepository.findFoodByName(name).orElseThrow(() -> new DataError("Food is not found"));
        return food;
    }
}
