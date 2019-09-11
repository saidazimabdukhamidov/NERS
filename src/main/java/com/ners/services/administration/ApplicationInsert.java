package com.ners.services.administration;

import com.google.gson.JsonObject;
import com.ners.utils.DataBase;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.CallableStatement;
import java.sql.Connection;

@Service
public class ApplicationInsert {
  @Autowired
  HikariDataSource hds;

  public String insertApplication(HttpServletRequest request) {
    JsonObject json = new JsonObject();
    Connection conn = null;
    CallableStatement cs = null;
    try {
      String full_name = request.getParameter("full_name");
      String birth_date = request.getParameter("birth_date");
      String study_place = request.getParameter("study_place");
      String subject = request.getParameter("subject");
      String class_time = request.getParameter("class_time");
      String parent_name = request.getParameter("parent_name");
      String address = request.getParameter("address");
      String phone_number = request.getParameter("phone_number");
      String found_where = request.getParameter("found_where");
      String state = request.getParameter("state");
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL APPLICANT_INSERT_P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
      cs.setString(1, full_name);
      cs.setString(2, birth_date);
      cs.setString(3, study_place);
      cs.setString(4, subject);
      cs.setString(5, class_time);
      cs.setString(6, parent_name);
      cs.setString(7, address);
      cs.setString(8, phone_number);
      cs.setString(9, found_where);
      cs.setString(10, state);
      int result = cs.executeUpdate();
      if (result > 0) {
        json.addProperty("location", "/registration-table");
      } else {
        json.addProperty("msg", "Ошибка!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(cs);
      DataBase.close(conn);
    }
    return "registration-table";
  }
}
