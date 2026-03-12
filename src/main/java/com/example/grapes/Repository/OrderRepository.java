package com.example.grapes.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.grapes.Model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findTopByOrderByIdDesc();

    List<Order> findAllByOrderByIdDesc();

    List<Order> findByUsernameOrderByIdDesc(String username);
}