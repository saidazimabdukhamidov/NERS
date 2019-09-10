package com.ners.services.accounting;

import com.ners.modules.transactions;
import com.ners.utils.dataBase;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Service
public class expenses {
  @Autowired
  HikariDataSource hds;

  public String readExpenses(Model model) {
    ArrayList<transactions> transaction = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_TRANSACTIONS");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        transactions t = new transactions();
        t.setTransaction_id(rs.getInt("transaction_id"));
        t.setSend_by(rs.getString("send_by"));
        t.setReason(rs.getString("reason"));
        t.setSend_to(rs.getString("send_to"));
        t.setFund(rs.getInt("fund"));
        t.setSend_time(rs.getDate("send_time"));
        transaction.add(t);
      }
      model.addAttribute("transactions", transaction);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataBase.close(rs);
      dataBase.close(ps);
      dataBase.close(conn);
    }
    return "transactions";
  }

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
        json.addProperty("location", "/transactions-list");
      } else {
        json.addProperty("msg", "Ошибка!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataBase.close(cs);
      dataBase.close(conn);
    }
    return json.toString();
  }
}
