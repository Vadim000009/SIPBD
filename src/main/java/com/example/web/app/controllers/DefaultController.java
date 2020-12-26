package com.example.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DefaultController {
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("astrav")) {
            return "redirect:/admin/";
        } else if (request.isUserInRole("USER_Department")) {
            return "redirect:/department.html";
        } else if (request.isUserInRole("USER_Design")) {
            return "redirect:/design.html";
        } else if (request.isUserInRole("USER_Employee")) {
            return "redirect:/employee.html";
        } else if (request.isUserInRole("USER_Storage")) {
            return "redirect:/storage.html";
        } else if (request.isUserInRole("USER_Test")) {
            return "redirect:/tester.html";
        } else if (request.isUserInRole("USER_Workshop")) {
            return "redirect:/workshop.html";
        }
        return "redirect:/home.html";
    }
}
