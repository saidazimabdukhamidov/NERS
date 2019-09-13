package com.ners.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Login {
  @GetMapping("/")
  public String login() {
    return "login-page";
  }
}