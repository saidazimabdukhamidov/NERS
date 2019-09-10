package com.ners.services.administration;

import com.ners.modules.applicant;
import com.ners.utils.dataBase;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Service
public class applicationRead {
  @Autowired
  HikariDataSource hds;

  public String readApplication(Model model) {
    ArrayList<applicant> applicants = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.APPLICANT");
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
        app.setParent_time(rs.getString("parent_time"));
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
    return "registration-table";

  }
}
