package com.coursework.eshopkursinisbackend.controllers;

import com.coursework.eshopkursinisbackend.dto.OrderDTO;
import com.coursework.eshopkursinisbackend.exceptions.OrderNotFoundException;
import com.coursework.eshopkursinisbackend.model.CustomerOrder;
import com.coursework.eshopkursinisbackend.repos.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class OrderRest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private Validator validator;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    @GetMapping(value = "/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<CustomerOrder> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = orders.stream()
                .map(order -> new OrderDTO(order.getId(), order.getDateCreated(), order.getOrderStatus(), order.getTotalPrice()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping(value = "/order/{id}")
    public ResponseEntity<EntityModel<OrderDTO>> getOrderById(@PathVariable(name = "id") int id) {
        CustomerOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        OrderDTO orderDTO = new OrderDTO(order.getId(), order.getDateCreated(), order.getOrderStatus(), order.getTotalPrice());
        EntityModel<OrderDTO> entityModel = EntityModel.of(orderDTO,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderRest.class).getOrderById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderRest.class).getAllOrders()).withRel("orders"));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping(value = "deleteOrder/{id}")
    public ResponseEntity<String> deleteOrderByID(@PathVariable(name = "id") int id) {
        CustomerOrder order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        detachAssociations(order);
        orderRepository.deleteById(id);
        return new ResponseEntity<>("Order with id = " + id + " was successfully deleted", HttpStatus.OK);
    }

    private void detachAssociations(CustomerOrder order) {
        if (order.getCustomer() != null) {
            order.setCustomer(null);
        }

        if (order.getResponsibleManager() != null) {
            order.setResponsibleManager(null);
        }

        if (order.getInOrderBoardGames() != null) {
            order.getInOrderBoardGames().clear();
        }
        if (order.getInOrderPuzzles() != null) {
            order.getInOrderPuzzles().clear();
        }
        if (order.getInOrderDices() != null) {
            order.getInOrderDices().clear();
        }

        if (order.getOrderChat() != null) {
            order.getOrderChat().clear();
        }
    }

    @PostMapping(value = "/createOrder")
    public ResponseEntity<?> createOrder(@Valid @RequestBody String orderInfo) {
        try {
            CustomerOrder order = objectMapper.readValue(orderInfo, CustomerOrder.class);

            Set<ConstraintViolation<CustomerOrder>> violations = validator.validate(order);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            orderRepository.saveAndFlush(order);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getConstraintViolations().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "updateOrder/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable(name = "id") int id, @RequestBody CustomerOrder orderInfo) {

        CustomerOrder order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (orderInfo.getOrderStatus() != null) {
            order.setOrderStatus(orderInfo.getOrderStatus());
        }
        if (orderInfo.getResponsibleManager() != null) {
            order.setResponsibleManager(orderInfo.getResponsibleManager());
        }

        Set<ConstraintViolation<CustomerOrder>> violations = validator.validate(order);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        orderRepository.save(order);
        OrderDTO orderDTO = new OrderDTO(order.getId(), order.getDateCreated(), order.getOrderStatus(), order.getTotalPrice());
        EntityModel<OrderDTO> entityModel = EntityModel.of(orderDTO,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderRest.class).getOrderById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderRest.class).getAllOrders()).withRel("orders"));

        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }


}
