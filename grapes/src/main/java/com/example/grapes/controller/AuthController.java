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

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("grapes", new Grapes());
        return "login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("grapes") Grapes grapes) {
        service.register(grapes);
        return "redirect:/add?registered=true";
    }

    @PostMapping("/login")
    public String login(@RequestParam String name,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Grapes user = service.login(name, password);

        if (user != null) {
            session.setAttribute("loggedUser", user);
            return "redirect:/home";
        }

        model.addAttribute("error", "Invalid username or password");
        model.addAttribute("grapes", new Grapes());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/add";
    }
}
