package com.ners.services.administration;

import com.ners.modules.Applicant;
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
public class ApplicationRead {
  @Autowired
  HikariDataSource hds;

  public String readApplication(Model model) {
    ArrayList<Applicant> applicants = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.APPLICANT");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Applicant a = new Applicant();
        a.setApplicant_id(rs.getInt("applicant_id"));
        a.setFull_name(rs.getString("full_name"));
        a.setBirth_date(rs.getString("birth_date"));
        a.setStudy_place(rs.getString("study_place"));
        a.setSubject(rs.getString("subject"));
        a.setChosen_time(rs.getString("chosen_time"));
        a.setParent_time(rs.getString("parent_time"));
        a.setAddress(rs.getString("address"));
        a.setPhone_number(rs.getString("phone_number"));
        a.setFound_where(rs.getString("found_where"));
        a.setState(rs.getString("state"));
        applicants.add(a);
      }
      model.addAttribute("applicants", applicants);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    return "registration-table";
  }
}
