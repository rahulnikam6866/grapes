package com.example.grapes.controller;

import com.example.grapes.Model.Grapes;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {

        Grapes user = (Grapes) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login"; // redirect if not logged in
        }

        model.addAttribute("user", user);
        return "home"; // render home.html
    }
}