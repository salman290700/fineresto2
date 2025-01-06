package org.babagroup.utils;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.Food;
import org.babagroup.models.Order;
import org.babagroup.models.OrderItem;
import org.babagroup.models.User;
import org.babagroup.repository.FoodRepository;
import org.babagroup.repository.OrderRepository;
import org.babagroup.repository.UserRepository;
import org.babagroup.resreq.OrderDto;
import org.babagroup.resreq.OrderItemDto;
import org.babagroup.resreq.UserResponse;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderItemUtils {
    @Inject
    FoodRepository foodRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    JsonWebToken jsonWebToken;

    public OrderItem mapToObject(OrderItemDto dto) {
        OrderItem item = new OrderItem();
        item.setId(UUID.randomUUID().toString());
        Food food = foodRepository.findFoodByName(dto.getFoodName()).orElseThrow(() -> new DataError("Data not found"));
        item.setFood(food);
        item.setQuantity(dto.getQuantity());
        User user = userRepository.findByEmail(jsonWebToken.getSubject()).orElseThrow(() -> new DataError("User is not found"));
        item.setCreatedBy(user.getId());
        item.setUpdatedBy(user.getId());
        item.setCreatedAt(new Date());
        item.setUpdatedAt(new Date());
        item.setPrice(dto.getPrice());
        item.setTotalPrice(dto.getPrice() * dto.getQuantity());
        return item;
    }

    public OrderItemDto mapToDto(OrderItem orderItem) {
        OrderItemDto dto = new OrderItemDto(orderItem.getId(), orderItem.getFood().getName(), orderItem.getQuantity(), orderItem.getPrice(), orderItem.getTotalPrice(), orderItem.getCustomer().getId(), orderItem.getFood().getImages());
        return dto;
    }

    public List<OrderItemDto> listToDto(List<OrderItem> items) {
        List<OrderItemDto> itemDtos = new ArrayList<>();

        for(OrderItem item: items) {
            OrderItemDto dto = mapToDto(item);
            itemDtos.add(dto);
        }
        return itemDtos;
    }
}
