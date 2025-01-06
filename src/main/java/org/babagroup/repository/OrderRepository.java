package org.babagroup.repository;

import org.babagroup.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findById(String id);

    @Override
    Page<Order> findAll(Pageable pageable);

    Page<Order> findByRestaurantId(String resto_id, Pageable pageable);
}
