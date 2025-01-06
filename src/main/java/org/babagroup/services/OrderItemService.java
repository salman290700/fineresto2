package org.babagroup.services;

import org.babagroup.models.Order;
import org.babagroup.models.OrderItem;
import org.babagroup.resreq.OrderDto;
import org.babagroup.resreq.OrderItemDto;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> findOrderItemByOrderId(String id);
    List<OrderItemDto> getOrderDto(List<OrderItem> orderItemList);
    OrderItemDto mapToDto(OrderItem orderItem);
    OrderItem mapToObject(OrderItemDto orderItemDto);
    OrderItemDto getOrderItemById(String id);
    boolean checkUserAdmin(Order order);
    OrderItemDto addOrderItem(OrderItemDto req);
    OrderItemDto updateOrderItem(OrderItemDto req);
    OrderItem findOrderById(String id);
}
