package com.example.grapes.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.grapes.admin.model.Product;
import com.example.grapes.admin.repository.AdminRepository;
import com.example.grapes.admin.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

import com.example.grapes.Repository.OrderRepository;
import com.example.grapes.Repository.GrapesRepository;
import com.example.grapes.Model.Admin;
import com.example.grapes.Model.Grapes;
import com.example.grapes.Model.Order;



@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    GrapesRepository grapesRepository;

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admin-login")
    public String showAdminLogin(Model model) {
        model.addAttribute("admin", new Admin()); // bind to form
        return "admin/admin-login"; // admin-login.html in templates
    }
    
    @PostMapping("/admin-login")
    public String adminLogin(@ModelAttribute Admin admin, HttpSession session,
                             Model model) {

        Admin existingAdmin = adminRepository.findByNameAndPassword(
                admin.getName(), admin.getPassword());

        if (existingAdmin != null) {
            session.setAttribute("adminUser", existingAdmin);
            return "redirect:/admin/dashboard"; // admin panel page
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "admin/admin-login";
        }
    }

    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("products", productRepository.count());
        model.addAttribute("orders", orderRepository.count());
        model.addAttribute("users", grapesRepository.count());
        return "admin/dashboard";
    }

    // ================= PRODUCTS =================
    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "admin/products";
    }

    @GetMapping("/products/add")
    public String addProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "admin/add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        return "admin/add-product"; // reuse add-product form for editing
    }
    // ================= USERS =================
   
    @GetMapping("/users")
    public String users(Model model) {

        List<Grapes> users = grapesRepository.findAll();
        model.addAttribute("users", users);

        return "admin/users";
    }
    // ================= ORDERS =================
    @GetMapping("/orders")
    public String orders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @GetMapping("/orders/update/{id}/{status}")
    public String updateOrderStatus(@PathVariable("id") Long orderId,
                                    @PathVariable("status") String status) {

        // Fetch the order
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status); // Set new status
            orderRepository.save(order);
        }

        // Redirect back to orders page
        return "redirect:/admin/orders";
    }
    @GetMapping("/payments")
    public String payments(Model model) {

        List<Order> orders = orderRepository.findAll();

        model.addAttribute("payments", orders);

        return "admin/payments";
    }
}