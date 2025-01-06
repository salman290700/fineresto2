package org.babagroup.services.implementations;

import jakarta.inject.Inject;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.*;
import org.babagroup.repository.AddressRepository;
import org.babagroup.repository.OrderRepository;
import org.babagroup.repository.RestaurantRepository;
import org.babagroup.resreq.OrderDto;
import org.babagroup.resreq.OrderItemDto;
import org.babagroup.services.OrderItemService;
import org.babagroup.services.OrderService;
import org.babagroup.services.UserServices;
import org.babagroup.utils.AddressUtils;
import org.babagroup.utils.OrderItemUtils;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderImpl implements OrderService {
    @Inject
    RestaurantRepository restaurantRepository;

    @Inject
    OrderItemService orderItemService;

    @Inject
    AddressRepository addressRepository;

    @Inject
    UserServices userService;

    @Inject
    OrderRepository orderRepository;

    OrderItemUtils orderItemUtils = new OrderItemUtils();

    AddressUtils addressUtils = new AddressUtils();

    @Override
    public List<OrderDto> getAllOrders(Pageable pageable) {
        String restaurantId = userService.getUserByEmail().getRestaurant().getId();
        List<OrderDto> orderDtoList = new ArrayList<>();

        List<Order> orders = orderRepository.findByRestaurantId(restaurantId, pageable).getContent();
        for (Order order: orders) {
            OrderDto orderDto = mapToDto(order);
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

    @Override
    public Order findOrderById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new DataError("Your order is not found"));
    }

    @Override
    public OrderDto mapToDto(Order order) {
        return new OrderDto(order.getId(), order.getAddress().getStreet(), order.getAddress().getState(), order.getAddress().getCountry(), order.getAddress().getCity(), orderItemService.getOrderDto(order.getItems()), order.getStatus(), order.getCustomer().getFullname());
    }

    @Override
    public Order mapToObject(OrderDto orderDto) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
//        Create address
        Address address = new Address();
        address.setCity(orderDto.getCity());
        address.setCountry(orderDto.getCountry());
        address.setState(orderDto.getState());
        address.setStreet(orderDto.getStreet());
        address.setId(UUID.randomUUID().toString());
        User user =  userService.getUserByEmail();
        address.setCreatedBy(user.getId());
        address.setCreatedAt(new Date());
        address.setUpdatedBy(user.getId());
        address.setUpdatedAt(new Date());
        Address findAddress = addressRepository.findAddressByCountryAndStateAndCityAndStreet(orderDto.getCountry(), orderDto.getState(), orderDto.getCity(), orderDto.getState()).orElse(addressRepository.save(address));
        order.setAddress(findAddress);
        order.setCustomer(user);
//        Items
        List<OrderItem> items = new ArrayList<>();
        for(OrderItemDto dto: orderDto.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem = orderItemUtils.mapToObject(dto);
            items.add(orderItem);
        }
        order.setItems(items);
        order.setOwner(user);
        order.setStatus("ACCEPTED");
        Restaurant restaurant = restaurantRepository.findById(user.getRestaurant().getId()).orElseThrow(() -> new DataError("404 Restaurant not found"));
        order.setRestaurant(restaurant);
        return order;
    }

    @Override
    public OrderDto updateOrderItem(OrderDto req) {
        Order order = findOrderById(req.getId());
        if(order.getAddress().getStreet() != req.getStreet()) {
            order.getAddress().setStreet(req.getStreet());
        }

        if(order.getStatus() != req.getStatus()) {
            order.setStatus(req.getStatus());
        }

        Order savedOrder = orderRepository.save(order);
        return mapToDto(savedOrder);
    }
}
