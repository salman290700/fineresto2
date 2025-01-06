package org.babagroup.repository;

import org.babagroup.models.Restaurant;
import org.babagroup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, String> {
    Optional<Restaurant> findByAdminId(String UserId);
    @Query("select r from restaurant where r.admin_id")
    Optional<List<Restaurant>> findRestaurantByAdminId(String user);
}
