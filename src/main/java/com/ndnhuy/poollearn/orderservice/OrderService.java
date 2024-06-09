package com.ndnhuy.poollearn.orderservice;

import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Transactional
    @SneakyThrows
    public Order createOrder(String name) {
        var order = new Order();
        order.setStatus(OrderStatus.INIT);
        order.setName(name);
        orderRepository.save(order);
        Thread.sleep(1000);
        return order;
    }

}
