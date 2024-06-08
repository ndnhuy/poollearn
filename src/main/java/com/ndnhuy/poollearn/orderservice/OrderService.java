package com.ndnhuy.poollearn.orderservice;

import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(String name) {
        var order = new Order();
        order.setStatus(OrderStatus.INIT);
        order.setName(name);
        orderRepository.save(order);
        return order;
    }

}
