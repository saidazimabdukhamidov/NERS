package com.ners.services.director;

import com.ners.modules.Students;
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
public class StudentRead {
  @Autowired
  HikariDataSource hds;

  public String readStudent(Model model) {
    ArrayList<Students> student = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.STUDENTS");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Students s = new Students();
        s.setStudent_id(rs.getInt("student_id"));
        s.setFull_name(rs.getString("full_name"));
        s.setGroup_number(rs.getString("group_number"));
        s.setPhone_number(rs.getString("phone_number"));
        s.setBirth_date(rs.getString("phone_number"));
        s.setSubject_study(rs.getString("subject_study"));
        s.setChosen_time(rs.getString("chosen_time"));
        s.setDays_study(rs.getString("days_study"));
        s.setDept(rs.getInt("dept"));
        s.setTeacher_name(rs.getString("teacher_name"));
        s.setSubject_price(rs.getInt("subject_price"));
        student.add(s);
      }
      model.addAttribute("students", student);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    return "students-table";
  }
}
