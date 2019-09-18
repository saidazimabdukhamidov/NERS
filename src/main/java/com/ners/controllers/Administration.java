package com.ners.controllers;

import com.ners.modules.Dept;
import com.ners.modules.Found;
import com.ners.modules.Subjects;
import com.ners.modules.Time;
import com.ners.services.administration.ApplicationCrud;
import com.ners.utils.DataBase;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class Administration {
  @Autowired
  ApplicationCrud applicationCrud;
  @Autowired
  HikariDataSource hds;

  Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;

  @PostMapping("/application-insert")
  @ResponseBody
  public String insertApplication(HttpServletRequest request) {
    return applicationCrud.insertApplication(request);
  }

  @GetMapping("/application-insert")
  public String addApplication(Model model) {
    ArrayList<Dept> depts = new ArrayList<>();
    ArrayList<Subjects> subjects = new ArrayList<>();
    ArrayList<Time> times = new ArrayList<>();
    ArrayList<Found> founds = new ArrayList<>();
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_DEPT");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Dept d = new Dept();
        d.setState_id(rs.getInt("state_id"));
        d.setState_type(rs.getString("state_type"));
        depts.add(d);
      }
      model.addAttribute("depts", depts);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_SUBJECTS");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Subjects s = new Subjects();
        s.setSubject_id(rs.getInt("subject_id"));
        s.setSubject_name(rs.getString("subject_name"));
        s.setSubject_teacher(rs.getString("subject_teacher"));
        s.setLevel(rs.getString("level"));
        s.setPrice(rs.getInt("price"));
        subjects.add(s);
      }
      model.addAttribute("subjects", subjects);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.CLASS_TIME");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Time t = new Time();
        t.setSubject_id(rs.getInt("subject_id"));
        t.setSubject_time(rs.getString("subject_time"));
        times.add(t);
      }
      model.addAttribute("times", times);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.FOUND_WHERE");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Found f = new Found();
        f.setType_id(rs.getInt("type_id"));
        f.setType_name(rs.getString("type_name"));
        founds.add(f);
      }
      model.addAttribute("founds", founds);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
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
