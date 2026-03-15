
package com.example.grapes.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaymentController {

	@GetMapping("/payment")
	public String paymentPage(HttpSession session) {
	    if (session.getAttribute("loggedUser") == null) {
	        return "redirect:/add";
	    }
	    return "payment";
	}


    @PostMapping("/pay")
    public String processPayment(HttpSession session) {
    	session.removeAttribute("cart");
        return "redirect:/thankyou";
    }

    @GetMapping("/thankyou")
    public String thankYouPage() {
        return "thankyou";
    }
}
