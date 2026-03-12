package com.example.grapes.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.grapes.Model.CartItem;
import com.example.grapes.Model.Grapes;
import com.example.grapes.Model.Order;
import com.example.grapes.Model.OrderItem;
import com.example.grapes.Repository.OrderItemRepository;
import com.example.grapes.Repository.OrderRepository;
import com.example.grapes.Service.OrderService;

@Controller
public class OrderController {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;
    // ✅ GET order page
    @GetMapping("/order-form")
    public String showOrderPage(HttpSession session, Model model) {

        Grapes user = (Grapes) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login"; // redirect to login if not logged in
        }

        Order order = new Order();
        order.setCustomerName(user.getName());
        order.setMobile(user.getMobile());

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = List.of();

        double subtotal = 0;
        for (CartItem item : cart) {
            subtotal += item.getPrice() * item.getQuantity();
        }
        double tax = subtotal * 0.05;
        double shipping = 50;
        double grandTotal = subtotal + tax + shipping;

        model.addAttribute("order", order);
        model.addAttribute("cart", cart);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("tax", tax);
        model.addAttribute("shipping", shipping);
        model.addAttribute("grandTotal", grandTotal);

        return "order-form"; // this must correspond to order.html in templates
    }

    // ✅ POST place order
    @PostMapping("/order")
    public String placeOrder(@ModelAttribute Order order, HttpSession session) {

        Grapes user = (Grapes) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }

        order.setUsername(user.getName());

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = List.of();

        double subtotal = 0;
        for (CartItem item : cart) {
            subtotal += item.getPrice() * item.getQuantity();
        }

        double tax = subtotal * 0.05;
        double shipping = 50;
        double grandTotal = subtotal + tax + shipping;

        order.setAmount(grandTotal);
        order.setStatus("Placed");

        Order savedOrder = orderService.saveOrder(order);

        // save order items
        for (CartItem item : cart) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductName(item.getProductName());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
        }

        session.removeAttribute("cart");

        // redirect with orderId
        return "redirect:/payment/" + savedOrder.getId();
    }

    // ✅ Optional: redirect old /order links to this controller
    @GetMapping("/place-order")
    public String redirectToOrder() {
        return "redirect:/order-form";
    }
    @GetMapping("/order-history")
    public String orderHistory(HttpSession session, Model model) {
        Grapes user = (Grapes) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login"; // use /login not /add
        }

        List<Order> orders = orderService.getOrdersByUsername(user.getName());
        model.addAttribute("orders", orders);

        return "order-history"; // this must match order-history.html
    }
    
    @GetMapping("/cancel-order/{id}")
    public String cancelOrder(@PathVariable Long id) {

        Order order = orderRepository.findById(id).orElse(null);

        if(order != null){
            order.setStatus("Cancelled");
            orderRepository.save(order);
        }

        return "redirect:/order-history";
    }
}