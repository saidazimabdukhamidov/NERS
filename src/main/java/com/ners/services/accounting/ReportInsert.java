package com.ners.services.accounting;

import com.google.gson.JsonObject;
import com.ners.utils.DataBase;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.CallableStatement;
import java.sql.Connection;

@Service
public class ReportInsert {
  @Autowired
  HikariDataSource hds;

  public String insertReport(HttpServletRequest request) {
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
