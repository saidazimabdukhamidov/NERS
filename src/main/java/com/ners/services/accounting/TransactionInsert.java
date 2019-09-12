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
public class TransactionInsert {
  @Autowired
  HikariDataSource hds;

  public String addTransaction(HttpServletRequest request) {
    JsonObject json = new JsonObject();
    Connection conn = null;
    CallableStatement cs = null;
    try {
      int transaction_id = 0;
      String send_by = request.getParameter("send_by");
      String reason = request.getParameter("reason");
      String send_to = request.getParameter("send_to");
      int fund = Integer.parseInt(request.getParameter("fund"));
      String send_time = request.getParameter("date");
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL NERS.TRANSACTION_ADD_P(?, ?, ?, ?, ?)}");
      cs.setString(1, send_by);
      cs.setString(2, reason);
      cs.setString(3, send_to);
      cs.setInt(4, fund);
      cs.setString(5, send_time);
      int result = cs.executeUpdate();
      if (result > 0) {
        json.addProperty("location", "/transaction-list");
      } else {
        json.addProperty("msg", "Ошибка!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(cs);
      DataBase.close(conn);
    }
    return "transaction-table";
  }
}
