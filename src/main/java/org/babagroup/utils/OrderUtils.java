package org.babagroup.utils;

import org.babagroup.models.Order;
import org.babagroup.resreq.OrderDto;

public class OrderUtils {
    OrderItemUtils orderItemUtils = new OrderItemUtils();
    public OrderDto mapToDto(Order order) {
        OrderDto dto = new OrderDto(order.getId(), order.getAddress().getStreet(), order.getAddress().getState(), order.getAddress().getCountry(), order.getAddress().getCity(), orderItemUtils.listToDto(order.getItems()), order.getStatus(), order.getCustomer().getEmail());
        return dto;
    }
}
