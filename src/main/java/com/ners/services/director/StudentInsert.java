package com.ners.services.director;

import com.google.gson.JsonObject;
import com.ners.utils.DataBase;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.CallableStatement;
import java.sql.Connection;

@Service
public class StudentInsert {
  @Autowired
  HikariDataSource hds;

  public String insertStudent(HttpServletRequest request) {
    JsonObject json = new JsonObject();
    Connection conn = null;
    CallableStatement cs = null;
    try {
      String full_name = request.getParameter("full_name");
      String group_number = request.getParameter("group_number");
      String phone_number = request.getParameter("phone_number");
      String birth_date = request.getParameter("birth_date");
      String subject_study = request.getParameter("subject_study");
      String chosen_time = request.getParameter("chosen_time");
      String days_study = request.getParameter("days_study");
      int dept = Integer.parseInt(request.getParameter("dept"));
      String teacher_name = request.getParameter("teacher_name");
      int subject_price = Integer.parseInt(request.getParameter("subject_price"));
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL }");
      cs.setString(1, full_name);
      cs.setString(2, group_number);
      cs.setString(3, phone_number);
      cs.setString(4, birth_date);
      cs.setString(5, subject_study);
      cs.setString(6, chosen_time);
      cs.setString(7, birth_date);
      cs.setString(8, phone_number);
      cs.setString(9, days_study);
      cs.setInt(10, dept);
      cs.setString(11, teacher_name);
      cs.setInt(12, subject_price);
      int result = cs.executeUpdate();
      if (result > 0) {
        json.addProperty("location", "/students-table");
      } else {
        json.addProperty("msg", "Ошибка!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(cs);
      DataBase.close(conn);
    }
    return "students-table";
  }
}
