package com.ners.services.director;

import com.ners.modules.Subjects;
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
public class SubjectRead {
  @Autowired
  HikariDataSource hds;

  public String readSubject(Model model) {
    ArrayList<Subjects> subject = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
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
}
