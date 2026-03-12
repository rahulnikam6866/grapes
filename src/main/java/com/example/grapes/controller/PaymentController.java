
package com.example.grapes.controller;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.grapes.Model.Order;
import com.example.grapes.Model.OrderItem;
import com.example.grapes.Repository.OrderItemRepository;
import com.example.grapes.Repository.OrderRepository;

@Controller
public class PaymentController {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@GetMapping("/payment/{orderId}")
	public String paymentPage(@PathVariable Long orderId, Model model, HttpSession session) {

	    if (session.getAttribute("loggedUser") == null) {
	        return "redirect:/login";
	    }

	    Order order = orderRepository.findById(orderId).orElse(null);

	    List<OrderItem> items = orderItemRepository.findByOrder(order);

	    model.addAttribute("order", order);
	    model.addAttribute("items", items);

	    return "payment";
	}

	@PostMapping("/pay")
	public String processPayment(
	        @RequestParam String paymentMethod,
	        HttpSession session) {

	    Order lastOrder = orderRepository.findTopByOrderByIdDesc();

	    if(lastOrder != null){

	        lastOrder.setPaymentMethod(paymentMethod);
	        lastOrder.setPaymentStatus("Paid");

	        orderRepository.save(lastOrder);
	    }

	    session.removeAttribute("cart");

	    return "redirect:/thankyou";
	}
    @GetMapping("/thankyou")
    public String thankYouPage(HttpSession session, Model model) {

        // Get last order
        Order lastOrder = orderRepository
                .findTopByOrderByIdDesc();

        if (lastOrder != null) {

            List<OrderItem> items =
                    orderItemRepository.findByOrder(lastOrder);

            model.addAttribute("order", lastOrder);
            model.addAttribute("items", items);
        }

        return "thankyou";
    }
}
