package com.ners.controllers;

import com.ners.services.administration.applicationDelete;
import com.ners.services.administration.applicationInsert;
import com.ners.services.administration.applicationRead;
import com.ners.services.administration.applicationUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class administration {
  @Autowired
  applicationInsert applicationInsert;
  @Autowired
  applicationUpdate applicationUpdate;
  @Autowired
  applicationDelete applicationDelete;
  @Autowired
  applicationRead applicationRead;

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
