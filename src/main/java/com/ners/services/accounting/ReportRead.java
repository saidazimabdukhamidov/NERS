package com.ners.services.accounting;

import com.ners.modules.Reports;
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
public class ReportRead {
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
    return "transaction-table";
  }
}
