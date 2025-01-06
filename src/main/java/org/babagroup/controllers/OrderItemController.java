package org.babagroup.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.babagroup.models.OrderItem;
import org.babagroup.resreq.OrderItemDto;
import org.babagroup.services.OrderItemService;
import org.babagroup.services.UserServices;

@RequestScoped
@Path("/order-item")
public class OrderItemController {

    @Inject
    OrderItemService orderItemService;

    @Path("/{id}")
    @GET
    public Response getOrderItem(@PathParam("id") String id) {
        return Response.ok(orderItemService.getOrderItemById(id)).build();
    }

    @Path("/{id}")
    @PUT
    public Response updateOrderItem(@PathParam("id") String id,
                                    OrderItemDto req) {
        OrderItemDto res = orderItemService.updateOrderItem(req);
        return Response.ok(res).build();
    }

    @Path("/add-order-item")
    @POST
    public Response addOrderItem(OrderItemDto req) {
        OrderItemDto res= orderItemService.addOrderItem(req);
        if(res.equals(null)) {
            return Response.noContent().build();
        }
        return Response.ok(res).build();
    }
}
