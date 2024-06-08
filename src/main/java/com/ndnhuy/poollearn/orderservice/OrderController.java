package com.ndnhuy.poollearn.orderservice;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder() {
        var order = orderService.createOrder(UUID.randomUUID().toString());
        return ResponseEntity.ok(order);
    }

}
