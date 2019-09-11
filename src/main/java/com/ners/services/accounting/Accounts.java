package com.ners.services.accounting;

import com.google.gson.JsonObject;
import com.ners.modules.Reports;
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
public class Accounts {
  @Autowired
  HikariDataSource hds;

  public String readReports(Model model) {
    ArrayList<Reports> report = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_REPORT");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Reports r = new Reports();
        r.setReport_id(rs.getInt("report_id"));
        r.setReport_name(rs.getString("report_name"));
        r.setFund(rs.getInt("fund"));
        r.setCreated_by(rs.getString("created_by"));
        r.setCreated_time(rs.getDate("created_time"));
        report.add(r);
      }
      model.addAttribute("reports", report);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    return "reports";
  }

  public String addReport(HttpServletRequest request) {
    JsonObject json = new JsonObject();
    Connection conn = null;
    CallableStatement cs = null;
    try {
      int report_id = 0;
      String report_name = request.getParameter("report_name");
      int fund = Integer.parseInt(request.getParameter("fund"));
      String created_by = request.getParameter("created_by");
      String created_time = request.getParameter("created_time");
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL NERS.REPORT_ADD_P(?, ?, ?, ?)}");
      cs.setString(1, report_name);
      cs.setInt(2, fund);
      cs.setString(3, created_by);
      cs.setString(4, created_time);
      int result = cs.executeUpdate();
      if (result > 0) {
        json.addProperty("location", "/report-list");
      } else {
        json.addProperty("msg", "Ошибка!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(cs);
      DataBase.close(conn);
    }
    return json.toString();
  }
}
