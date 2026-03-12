
package com.example.grapes.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grapes.Model.Order;
import com.example.grapes.Model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	List<OrderItem> findByOrder(Order order);
}