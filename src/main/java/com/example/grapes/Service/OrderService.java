package com.example.grapes.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.grapes.Model.Order;
import com.example.grapes.Repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Save order
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Get  orders (for Order History page)
    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.findByUsernameOrderByIdDesc(username);
    }
    
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}