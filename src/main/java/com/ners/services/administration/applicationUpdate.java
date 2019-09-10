package com.ners.services.administration;

import com.google.gson.JsonObject;
import com.ners.modules.applicant;
import com.ners.utils.dataBase;
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
public class applicationUpdate {
  @Autowired
  HikariDataSource hds;

  public String editApplication(HttpServletRequest request, Model model) {
    ArrayList<applicant> applicants = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.APPLICANT WHERE APPLICANT_ID = ?");
      ps.setInt(1, Integer.parseInt(request.getParameter("applicant_id")));
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        applicant app = new applicant();
        app.setApplicant_id(rs.getInt("applicant_id"));
        app.setFull_name(rs.getString("full_name"));
        app.setBirth_date(rs.getString("birth_date"));
        app.setStudy_place(rs.getString("study_place"));
        app.setSubject(rs.getString("subject"));
        app.setChosen_time(rs.getString("chosen_time"));
        app.setParent_time(rs.getString("parent_name"));
        app.setAddress(rs.getString("address"));
        app.setPhone_number(rs.getString("phone_number"));
        app.setFound_where(rs.getString("found_where"));
        app.setState(rs.getString("state"));
        applicants.add(app);
      }
      model.addAttribute("applicants", applicants);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataBase.close(rs);
      dataBase.close(ps);
      dataBase.close(conn);
    }
    return "edit-application";
  }

  public String updateApplication(HttpServletRequest request) {
    JsonObject json = new JsonObject();
    Connection conn = null;
    CallableStatement cs = null;
    try {
      int applicant_id = Integer.parseInt(request.getParameter("applicant_id"));
      String full_name = request.getParameter("full_name");
      String birth_date = request.getParameter("birth_date");
      String study_place = request.getParameter("study_place");
      String subject = request.getParameter("subject");
      String chosen_time = request.getParameter("chosen_time");
      String parent_name = request.getParameter("parent_name");
      String address = request.getParameter("address");
      String phone_number = request.getParameter("phone_number");
      String found_where = request.getParameter("found_where");
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL APPLICANT_UPDATE_P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
      cs.setInt(1, applicant_id);
      cs.setString(2, full_name);
      cs.setString(3, birth_date);
      cs.setString(4, study_place);
      cs.setString(5, subject);
      cs.setString(6, chosen_time);
      cs.setString(7, parent_name);
      cs.setString(8, address);
      cs.setString(9, phone_number);
      cs.setString(10, found_where);
      int result = cs.executeUpdate();
      if (result > 0) {
        json.addProperty("location", "/registration-table");
      } else {
        json.addProperty("msg", "Ошибка!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataBase.close(cs);
      dataBase.close(conn);
    }
    return "registration-table";
  }
}
