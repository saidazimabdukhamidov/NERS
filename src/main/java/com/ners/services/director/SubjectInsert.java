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
public class SubjectInsert {
  @Autowired
  HikariDataSource hds;

  public String insertSubject(HttpServletRequest request) {
    JsonObject json = new JsonObject();
    Connection conn = null;
    CallableStatement cs = null;
    try {
      int subject_id = 0;
      String subject_name = request.getParameter("subject_name");
      String subject_teacher = request.getParameter("subject_teacher");
      String level = request.getParameter("level");
      int price = Integer.parseInt(request.getParameter("price"));
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL SUBJECT_INSER_P(?, ?, ?, ?)}");
      cs.setString(1, subject_name);
      cs.setString(2, subject_teacher);
      cs.setString(3, level);
      cs.setInt(4, price);
      int result = cs.executeUpdate();
      if (result > 0) {
        json.addProperty("location", "/subjects-table");
      } else {
        json.addProperty("msg", "Ошибка!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(cs);
      DataBase.close(conn);
    }
    return "subjects-table";
  }
}
