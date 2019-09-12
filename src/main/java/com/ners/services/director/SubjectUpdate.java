package com.ners.services.director;

import com.google.gson.JsonObject;
import com.ners.modules.Subjects;
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
public class SubjectUpdate {
  @Autowired
  HikariDataSource hds;

  public String editSubject(Model model) {
    ArrayList<Subjects> subject = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_SUBJECTS WHERE SUBJECT_ID = ?");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Subjects s = new Subjects();
        s.setSubject_name(rs.getString("subject_name"));
        s.setSubject_teacher(rs.getString("subject_teacher"));
        s.setLevel(rs.getString("level"));
        s.setPrice(rs.getInt("price"));
        subject.add(s);
      }
      model.addAttribute("subjects", subject);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    return "subjects-table";
  }

  public String updateSubject(HttpServletRequest request) {
    JsonObject json = new JsonObject();
    Connection conn = null;
    CallableStatement cs = null;
    try {
      String subject_name = request.getParameter("subject_name");
      String subject_teacher = request.getParameter("subject_teacher");
      String level = request.getParameter("level");
      int price = Integer.parseInt(request.getParameter("price"));
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL }");
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
