package com.example.customerAdmin.controller;

import com.example.customerAdmin.model.LoginForm;
import com.example.customerAdmin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        if (loginService.authorize()) {
            return "redirect:/admin/users";
        }
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm) {
        loginService.authenticateAndSetAuthToken(loginForm.getEmail(), loginForm.getPassword());
        return "redirect:/admin/users";
    }
}
