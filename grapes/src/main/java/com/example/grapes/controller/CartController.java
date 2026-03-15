package com.example.grapes.controller;

import com.example.grapes.Model.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    // ✅ ADD TO CART
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam String productName,
                            @RequestParam double price,
                            @RequestParam int quantity,
                            HttpSession session) {

        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        boolean found = false;

        for (CartItem item : cart) {
            if (item.getProductName().equals(productName)) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            cart.add(new CartItem(productName, price, quantity));
        }

        session.setAttribute("cart", cart);

        return "redirect:/cart";
    }

    // ✅ VIEW CART
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {

        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        model.addAttribute("cartItems", cart);

        return "cart";
    }
}
