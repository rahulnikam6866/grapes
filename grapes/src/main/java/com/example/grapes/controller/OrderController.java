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
import com.example.grapes.Service.OrderService;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @GetMapping("/order")
    public String showOrderPage(HttpSession session, Model model) {

        // Get logged user from session
        Grapes user = (Grapes) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/add"; // if not logged in
        }

        Order order = new Order();

        // 🔥 SET name and mobile automatically
        order.setCustomerName(user.getName());
        order.setMobile(user.getMobile());

        // Get cart from session
        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        double subtotal = 0;

        if (cart != null) {
            for (CartItem item : cart) {
                subtotal += item.getPrice() * item.getQuantity();
            }
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

        return "order";
    }

     


    // HANDLE ORDER SUBMIT
    @PostMapping("/order")
    public String placeOrder(@ModelAttribute Order order,
                             HttpSession session) {

        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        double subtotal = 0;

        if (cart != null) {
            for (CartItem item : cart) {
                subtotal += item.getPrice() * item.getQuantity();
            }
            if (cart != null && !cart.isEmpty()) {
                CartItem firstItem = cart.get(0);
                order.setProductName(firstItem.getProductName());
                order.setQuantity(firstItem.getQuantity());
            }
        }

        double tax = subtotal * 0.05;
        double shipping = 50;
        double grandTotal = subtotal + tax + shipping;

        // 🔥 VERY IMPORTANT
        order.setAmount(grandTotal);

        orderService.saveOrder(order);


        return "redirect:/payment";  // Redirect to home
    }
}
