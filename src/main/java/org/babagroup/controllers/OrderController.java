package org.babagroup.controllers;

import io.quarkus.logging.Log;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.babagroup.exceptions.DataError;
import org.babagroup.models.*;
import org.babagroup.repository.AddressRepository;
import org.babagroup.repository.FoodRepository;
import org.babagroup.repository.OrderRepository;
import org.babagroup.resreq.OrderDto;
import org.babagroup.resreq.OrderItemDto;
import org.babagroup.services.OrderService;
import org.babagroup.services.RestaurantServices;
import org.babagroup.services.UserServices;
import org.babagroup.utils.OrderItemUtils;
import org.babagroup.utils.OrderUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
@Path("/order")
public class OrderController {
    @Inject
    UserServices userService;

    @Inject
    AddressRepository addressRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    RestaurantServices restaurantServices;

    @Inject
    OrderService orderService;

    @Inject
    FoodRepository foodRepository;

    OrderUtils orderUtils = new OrderUtils();

    OrderItemUtils orderItemUtils = new OrderItemUtils();

    @Path("/get-all-orders/{pageNumber}/pageSize")
    @RolesAllowed({"admin", "owner"})
    @GET
    public Response getAllOrders(@PathParam("pageNumber") int pageNumber,
                                 @PathParam("pageSize") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return Response.ok(orderService.getAllOrders(pageable)).build();
    }

    @Path("/find-order/{id}")
    @RolesAllowed({"admin", "owner"})
    @GET
    public Response findOrderById(@PathParam("id") String id) {
        Order order = orderService.findOrderById(id);
        return Response.ok(orderService.mapToDto(order)).build();
    }

    @Path("/update-order/{id}")
    @RolesAllowed({"admin", "owner"})
    @POST
    public Response updateOrderById(@PathParam("id") String id,
                                    OrderDto req) {
        Order order = orderRepository.findById(req.getId()).orElseThrow(() -> new DataError("Your order is not found"));
        User user = userService.getUserByEmail();
        Restaurant restaurant = restaurantServices.getRestaurant();
        User customer = userService.getUserByEmail();
        if(!user.getRestaurant().getId().equals(req.getItems()))
            order.setCreatedBy(user.getId());
        order.setUpdatedBy(user.getId());
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
//        Food Handler
        List<String> foodIds = new ArrayList<>();
        for(OrderItemDto dto: req.getItems()) {
            foodIds.add(dto.getId());
        }
        List<Food> foods = foodRepository.findAllById(foodIds);
//        Items handler
        List<OrderItem> items = new ArrayList<>();
        List<OrderItemDto> dtos = req.getItems();
        for(OrderItemDto dto: dtos) {
            OrderItem item = new OrderItem();
            item.setId(UUID.randomUUID().toString());
            item.setPrice(dto.getPrice());
            item.setCreatedAt(new Date());
            item.setUpdatedAt(new Date());
            item.setCreatedBy(user.getId());
            item.setUpdatedBy(user.getId());
            item.setQuantity(dto.getQuantity());
            item.setCustomer(customer);
//            getFood
            Food food = foods.stream().filter(filtered -> filtered.getId().equals(dto.getId())).collect(Collectors.toList()).get(0);
            item.setFood(food);
            item.setTotalPrice(dto.getQuantity() * dto.getPrice());
            items.add(item);
        }
        order.setItems(items);
        order.setOwner(customer);
        order.setRestaurant(restaurant);
        order.setStatus("ACCEPTED");
        order.setStatus("ACCEPTED");

        Address address = addressRepository.findAddressByCountryAndStateAndCityAndStreet(req.getCountry(), req.getState(), req.getCity(), req.getStreet()).orElse(null);
        if (address.equals(null)){
            address = new Address();
            address.setId(UUID.randomUUID().toString());
            address.setStreet(req.getStreet());
            address.setCity(req.getCity());
            address.setState(req.getStreet());
            address.setCountry(req.getCountry());
            address.setCreatedAt(new Date());
            address.setUpdatedAt(new Date());
            address.setCreatedBy(user.getId());
            address.setUpdatedBy(user.getId());
            address = addressRepository.save(address);
        }
        order.setAddress(address);
        try {
            Order savedOrder = orderRepository.save(order);
            order = savedOrder;
        }catch (Exception e) {
//            Save hacker
            Log.info(e.getMessage());
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setCity(order.getAddress().getCity());
        orderDto.setStreet(order.getAddress().getStreet());
        orderDto.setCountry(order.getAddress().getCountry());
        orderDto.setState(order.getAddress().getState());
        orderDto.setName(req.getName());
        orderDto.setStatus(order.getStatus());
        orderDto.setId(order.getId());
        return Response.ok(orderDto).build();
    }
    
    @Path("/create-order")
    @POST
    @RolesAllowed({"admin", "owner"})
    public Response createOrder(OrderDto req) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        User user = userService.getUserByEmail();
        Restaurant restaurant = restaurantServices.getRestaurant();
        User customer = userService.getUserByEmail();
        if(!user.getRestaurant().getId().equals(req.getItems()))
        order.setCreatedBy(user.getId());
        order.setUpdatedBy(user.getId());
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
//        Food Handler
        List<String> foodIds = new ArrayList<>();
        for(OrderItemDto dto: req.getItems()) {
            foodIds.add(dto.getId());
        }
        List<Food> foods = foodRepository.findAllById(foodIds);
//        Items handler
        List<OrderItem> items = new ArrayList<>();
        List<OrderItemDto> dtos = req.getItems();
        for(OrderItemDto dto: dtos) {
            OrderItem item = new OrderItem();
            item.setId(UUID.randomUUID().toString());
            item.setPrice(dto.getPrice());
            item.setCreatedAt(new Date());
            item.setUpdatedAt(new Date());
            item.setCreatedBy(user.getId());
            item.setUpdatedBy(user.getId());
            item.setQuantity(dto.getQuantity());
            item.setCustomer(customer);
//            getFood
            Food food = foods.stream().filter(filtered -> filtered.getId().equals(dto.getId())).collect(Collectors.toList()).get(0);
            item.setFood(food);
            item.setTotalPrice(dto.getQuantity() * dto.getPrice());
            items.add(item);
        }
        order.setItems(items);
        order.setOwner(customer);
        order.setRestaurant(restaurant);
        order.setStatus("ACCEPTED");
        order.setStatus("ACCEPTED");

        Address address = addressRepository.findAddressByCountryAndStateAndCityAndStreet(req.getCountry(), req.getState(), req.getCity(), req.getStreet()).orElse(null);
        if (address.equals(null)){
            address = new Address();
            address.setId(UUID.randomUUID().toString());
            address.setStreet(req.getStreet());
            address.setCity(req.getCity());
            address.setState(req.getStreet());
            address.setCountry(req.getCountry());
            address.setCreatedAt(new Date());
            address.setUpdatedAt(new Date());
            address.setCreatedBy(user.getId());
            address.setUpdatedBy(user.getId());
            address = addressRepository.save(address);
        }
        order.setAddress(address);
        try {
            Order savedOrder = orderRepository.save(order);
            order = savedOrder;
        }catch (Exception e) {
//            Save hacker
            Log.info(e.getMessage());
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setCity(order.getAddress().getCity());
        orderDto.setStreet(order.getAddress().getStreet());
        orderDto.setCountry(order.getAddress().getCountry());
        orderDto.setState(order.getAddress().getState());
        orderDto.setName(req.getName());
        orderDto.setStatus(order.getStatus());
        orderDto.setId(order.getId());
        return Response.ok(orderDto).build();
    }
}