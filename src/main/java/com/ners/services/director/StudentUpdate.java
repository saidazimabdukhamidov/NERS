package com.ners.services.director;

import com.google.gson.JsonObject;
import com.ners.modules.Students;
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
public class StudentUpdate {
  @Autowired
  HikariDataSource hds;

  public String editStudent(Model model) {
    ArrayList<Students> student = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.STUDENTS WHERE STUDENT_ID = ?");
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

  public String updateStudent(HttpServletRequest request) {
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
      cs = conn.prepareCall("{CALL STUDENT_UPDATE_P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
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
