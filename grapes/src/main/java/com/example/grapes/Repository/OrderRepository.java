
	package com.example.grapes.Repository;

	import org.springframework.data.jpa.repository.JpaRepository;
	import com.example.grapes.Model.Order;

	public interface OrderRepository extends JpaRepository<Order, Long> {
	}


