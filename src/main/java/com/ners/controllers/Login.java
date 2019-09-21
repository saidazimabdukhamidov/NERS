package com.ners.controllers;

import com.ners.services.authorisation.Authorisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Login {
  @Autowired
  Authorisation authorisation;

  @GetMapping("/")
  public String login() {
    return "login-page";
  }

  @PostMapping("/auth")
  @ResponseBody
  public String auth(HttpServletRequest request, Model model) {
    return authorisation.login(request, model);
  }
}
