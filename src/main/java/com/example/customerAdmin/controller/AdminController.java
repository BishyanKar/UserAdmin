package com.example.customerAdmin.controller;

import com.example.customerAdmin.model.User;
import com.example.customerAdmin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        List<User> userList = adminService.getAllUsers();
        if (Objects.isNull(userList)) {
            return "redirect:/login";
        }
        model.addAttribute("userList", userList);
        model.addAttribute("user", new User());
        return "admin";
    }

    @PostMapping("admin/users/add")
    public String addUser(@ModelAttribute User user, Model model) {
        String response = adminService.addUser(user);
        if (Objects.isNull(response)) {
            return "redirect:/login";
        }
        return "redirect:/admin/users";
    }

    @GetMapping("admin/users/update")
    public String updateUser(@RequestParam("id") String uuid, Model model) {
        model.addAttribute("user", adminService.getUserById(uuid));
        return "editUser";
    }

    @PostMapping("admin/users/update")
    public String updateUser(@ModelAttribute User user) {
        String response = adminService.updateUser(user);
        if (Objects.isNull(response)) {
            return "redirect:/login";
        }
        return "redirect:/admin/users";
    }

    @PostMapping("admin/users/delete")
    public String deleteUser(@RequestParam("id") String uuid) {
        String response = adminService.deleteUser(uuid);
        if (Objects.isNull(response)) {
            return "redirect:/login";
        }
        return "redirect:/admin/users";
    }
}
