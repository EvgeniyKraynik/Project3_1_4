package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;


@Controller
@RequestMapping()
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    String showIndex() {
        return "index";
    }

    @GetMapping("/user")
    String showUser(Principal principal, Model model) {
        model.addAttribute("user", userService.findUserByUsername(principal.getName()));
        return "user";
    }
}
