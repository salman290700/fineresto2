package org.babagroup.repository;

import org.babagroup.models.Order;
import org.babagroup.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepo extends JpaRepository<OrderItem, String> {
    Optional<OrderItem> findById(String id);
}
