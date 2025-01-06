package org.babagroup.services;

import org.babagroup.models.Order;
import org.babagroup.resreq.OrderDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders(Pageable pageable);
    Order findOrderById(String id);
    OrderDto mapToDto(Order order);
    Order mapToObject(OrderDto orderDto);
    OrderDto updateOrderItem(OrderDto req);
}
