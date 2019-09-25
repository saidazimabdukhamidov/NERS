package com.ners.services.authorisation;

import com.google.gson.JsonObject;
import com.ners.modules.Users;
import com.ners.utils.DataBase;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Service
public class Authorisation {
  @Autowired
  HikariDataSource hds;

  public String login(HttpServletRequest request, Model model) {
    ArrayList<Users> user = new ArrayList<>();
    JsonObject json = new JsonObject();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      json.addProperty("success", false);
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_USERS WHERE USERNAME = ? AND PASSWORD = ?");
      ps.setString(1, username);
      ps.setString(2, password);
      ps.execute();
      rs = ps.getResultSet();
      if (rs.next()) {
        json.addProperty("success", true);
      } else {
        json.addProperty("msg", "Login yoki parol xato!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    return json.toString();
  }
}
