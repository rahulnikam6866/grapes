
package com.example.grapes.controller;

import com.example.grapes.Model.Grapes;
import com.example.grapes.Model.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.ArrayList;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {

        Grapes user = (Grapes) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/add";
        }

        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        model.addAttribute("user", user);
        model.addAttribute("cart", cart);

        return "home";
    }
}
