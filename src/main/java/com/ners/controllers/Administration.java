package com.ners.controllers;

import com.ners.services.administration.ApplicationCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Administration {
  @Autowired
  ApplicationCrud applicationCrud;

  @PostMapping("/application-insert")
  @ResponseBody
  public String insertApplication(HttpServletRequest request) {
    return applicationCrud.insertApplication(request);
  }

  @GetMapping("/application-insert")
  public String addApplication(){
    return "application-insert";
  }

  @GetMapping("/application-update")
  public String editApplication(HttpServletRequest request, Model model) {
    return applicationCrud.editApplication(request, model);
  }

  @PostMapping("/application-update")
  @ResponseBody
  public String updateApplication(HttpServletRequest request) {
    return applicationCrud.updateApplication(request);
  }

  @GetMapping("/application-delete")
  public String deleteApplication(HttpServletRequest request) {
    return applicationCrud.deleteApplication(request);
  }

  @GetMapping("/application-list")
  public String readApplication(Model model) {
    return applicationCrud.readApplication(model);
  }

  @GetMapping("/archive-list")
  public String readArchive(Model model) {
    return applicationCrud.readArchive(model);
  }
}
