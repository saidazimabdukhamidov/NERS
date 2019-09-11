package com.ners.controllers;

import com.ners.services.administration.ApplicationDelete;
import com.ners.services.administration.ApplicationInsert;
import com.ners.services.administration.ApplicationRead;
import com.ners.services.administration.ApplicationUpdate;
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
  ApplicationInsert applicationInsert;
  @Autowired
  ApplicationUpdate applicationUpdate;
  @Autowired
  ApplicationDelete applicationDelete;
  @Autowired
  ApplicationRead applicationRead;

  @PostMapping("/application-insert")
  @ResponseBody
  public String insertApplication(HttpServletRequest request) {
    return applicationInsert.insertApplication(request);
  }

  @PostMapping("/application-update")
  @ResponseBody
  public String editApplication(HttpServletRequest request, Model model) {
    return applicationUpdate.editApplication(request, model);
  }

  @GetMapping("/application-update")
  public String updateApplication(HttpServletRequest request) {
    return applicationUpdate.updateApplication(request);
  }

  @GetMapping("/application-delete")
  public String deleteApplication(HttpServletRequest request) {
    return applicationDelete.deleteApplication(request);
  }

  @GetMapping("/application-list")
  public String readApplication(Model model) {
    return applicationRead.readApplication(model);
  }
}
