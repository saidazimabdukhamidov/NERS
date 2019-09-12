package com.ners.services.director;

import com.ners.modules.Users;
import com.ners.utils.DataBase;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Service
public class UserRead {
  @Autowired
  HikariDataSource hds;

  public String readUser(Model model) {
    ArrayList<Users> user = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_USERS");
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
    return "users-table";
  }
}
