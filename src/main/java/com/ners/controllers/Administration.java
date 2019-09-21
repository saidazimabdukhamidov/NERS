package com.ners.controllers;

import com.ners.services.administration.ApplicationCrud;
import com.zaxxer.hikari.HikariDataSource;
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
  @Autowired
  HikariDataSource hds;

  @PostMapping("/application-insert")
  @ResponseBody
  public String insertApplication(HttpServletRequest request) {
    return applicationCrud.insertApplication(request);
  }

  @GetMapping("/application-insert")
  public String addApplication(Model model) {
    return applicationCrud.readInsertApplication(model);
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

  @GetMapping("/application-archive")
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

  @GetMapping("/archive-restore")
  public String restoreArchive(HttpServletRequest request) {
    return applicationCrud.restoreArchive(request);
  }

  @GetMapping("/archive-delete")
  public String deleteArchive(HttpServletRequest request) {
    return applicationCrud.deleteArchive(request);
  }
}
