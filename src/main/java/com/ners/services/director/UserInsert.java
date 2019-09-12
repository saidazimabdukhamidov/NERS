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
public class UserInsert {
  @Autowired
  HikariDataSource hds;

  public String insertUser(HttpServletRequest request) {
    JsonObject json = new JsonObject();
    Connection conn = null;
    CallableStatement cs = null;
    try {
      String full_name = request.getParameter("full_name");
      int role_id = Integer.parseInt(request.getParameter("role_id"));
      int absence = Integer.parseInt(request.getParameter("absence"));
      String group_id = request.getParameter("group_id");
      int dept = Integer.parseInt(request.getParameter("dept"));
      String birth_date = request.getParameter("birth_date");
      String phone_number = request.getParameter("phone_number");
      String login = request.getParameter("login");
      String password = request.getParameter("password");
      int salary = Integer.parseInt(request.getParameter("salary"));
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL }");
      cs.setString(1, full_name);
      cs.setInt(2, role_id);
      cs.setInt(3, absence);
      cs.setString(4, group_id);
      cs.setString(5, group_id);
      cs.setInt(6, dept);
      cs.setString(7, birth_date);
      cs.setString(8, phone_number);
      cs.setString(9, login);
      cs.setString(10, password);
      cs.setInt(11, salary);
      int result = cs.executeUpdate();
      if (result > 0) {
        json.addProperty("location", "/users-table");
      } else {
        json.addProperty("msg", "Ошибка!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(cs);
      DataBase.close(conn);
    }
    return "users-table";
  }
}
