package com.ners.services.director;

import com.google.gson.JsonObject;
import com.ners.modules.Users;
import com.ners.utils.DataBase;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Service
public class UserUpdate {
  @Autowired
  HikariDataSource hds;

  public String editUser(Model model) {
    ArrayList<Users> user = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_USERS WHERE USER_ID = ?");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Users u = new Users();
        u.setUser_id(rs.getInt("user_id"));
        u.setFull_name(rs.getString("full_name"));
        u.setRole_id(rs.getInt("role_id"));
        u.setAbsence(rs.getInt("absence"));
        u.setGroup_id(rs.getString("group_id"));
        u.setDept(rs.getInt("dept"));
        u.setBirth_date(rs.getString("birth_date"));
        u.setPhone_number(rs.getString("phone_number"));
        u.setLogin(rs.getString("login"));
        u.setPassword(rs.getString("password"));
        u.setSalary(rs.getInt("salary"));
        user.add(u);
      }
      model.addAttribute("users", user);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    return "edit-user";
  }

  public String updateUser(HttpServletRequest request) {
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
