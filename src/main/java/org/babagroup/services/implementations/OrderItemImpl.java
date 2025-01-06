package org.babagroup.services.implementations;

import jakarta.inject.Inject;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.*;
import org.babagroup.repository.OrderItemRepo;
import org.babagroup.repository.OrderRepository;
import org.babagroup.resreq.OrderItemDto;
import org.babagroup.services.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderItemImpl implements OrderItemService {

    @Inject
    OrderItemRepo orderItemRepo;

    @Inject
    UserServices userService;

    @Inject
    FoodService foodService;

    @Inject
    OrderRepository orderRepository;

    @Override
    public List<OrderItem> findOrderItemByOrderId(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new DataError("Order is not found")).getItems();
    }

    @Override
    public List<OrderItemDto> getOrderDto(List<OrderItem> orderItemList) {
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for (OrderItem orderItem: orderItemList) {
            OrderItemDto orderItemDto = mapToDto(orderItem);
            orderItemDtoList.add(orderItemDto);
        }
        return orderItemDtoList;
    }

    @Override
    public OrderItemDto mapToDto(OrderItem orderItem) {
        Food food = orderItem.getFood();

        if(orderItem.getId().equals(null)) {
            orderItem.setId(UUID.randomUUID().toString());
        }

        OrderItemDto orderItemDto = new OrderItemDto(UUID.randomUUID().toString(), orderItem.getFood().getName(), orderItem.getQuantity(), orderItem.getPrice(), orderItem.getTotalPrice(), orderItem.getCustomer().getFullname(), orderItem.getFood().getImages());
        return orderItemDto;
    }

    @Override
    public OrderItem mapToObject(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        if(orderItem.getId().equals(null)) {
            orderItem.setId(UUID.randomUUID().toString());
        }else {
            orderItem.setId(orderItemDto.getId());
        }

        orderItem.setCustomer(userService.getUserByEmail());
        orderItem.setPrice(orderItem.getPrice());
        User user = userService.getUserByEmail();
        if(orderItem.getCreatedBy().equals(null)) {
            orderItem.setCreatedBy(user.getId());
            orderItem.setCreatedAt(new Date());
        }
        orderItem.setUpdatedBy(user.getId());
        orderItem.setUpdatedAt(new Date());
        orderItem.setQuantity(orderItem.getQuantity());
        orderItem.setTotalPrice(orderItemDto.getTotalPrice());
//        Needs Food Service
        orderItem.setFood(foodService.getFoodById(orderItemDto.getFoodName()));
        return orderItem;
    }

    @Override
    public OrderItemDto getOrderItemById(String id) {
        OrderItem orderItem = orderItemRepo.findById(id).orElseThrow(() -> new DataError("Order Item is not found"));
        return mapToDto(orderItem);
    }

    @Override
    public boolean checkUserAdmin(Order order) {
        String restaurantAdmin = userService.getUserByEmail().getRestaurant().getId();
        String order_restaurant = order.getRestaurant().getId();
        return restaurantAdmin.equals(order_restaurant);
    }

    @Override
    public OrderItemDto addOrderItem(OrderItemDto req) {
        OrderItem orderItem = mapToObject(req);
        OrderItem savedOrderItem = orderItemRepo.save(orderItem);
        return mapToDto(savedOrderItem);
    }

    @Override
    public OrderItemDto updateOrderItem(OrderItemDto req) {
        OrderItem orderItem = findOrderById(req.getId());
        return mapToDto(orderItem);
    }

    @Override
    public OrderItem findOrderById(String id) {
        return orderItemRepo.findById(id).orElseThrow(() -> new DataError("Order Item is not found"));
    }
}
