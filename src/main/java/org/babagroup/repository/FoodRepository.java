package org.babagroup.repository;

import org.babagroup.models.Food;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food,String> {
    Optional<Food> findFoodById(String id);
//    Optional<List<Food>> findFoodAll(Pageable pageable);
    Optional<Food> findFoodByName(String name);
    List<Food> findByRestaurantId(String restaurant_id);
}
