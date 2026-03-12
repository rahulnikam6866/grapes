package com.example.grapes.controller;

import com.example.grapes.Model.Grapes;
import com.example.grapes.Service.GrapesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private GrapesService service;

    // Show Registration Form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("grapes", new Grapes());
        return "login"; // use login.html with toggle
    }

    // Register POST
    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute("grapes") Grapes grapes) {
        service.register(grapes);
        return "redirect:/login?registered=true";
    }

    // Show Login Form
    @GetMapping("/login")
    public String showLoginForm(Model model, 
                                @RequestParam(value="registered", required=false) String registered) {
        model.addAttribute("grapes", new Grapes());
        model.addAttribute("param", registered); // for Thymeleaf message
        return "login";
    }

    // Login POST
    @PostMapping("/login")
    public String loginCustomer(@RequestParam String name,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {

        Grapes user = service.login(name, password);

        if (user != null) {
            session.setAttribute("loggedUser", user); // store in session
            return "redirect:/home"; // redirect to home page
        }

        model.addAttribute("error", "Invalid username or password");
        model.addAttribute("grapes", new Grapes());
        return "login";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}