package com.ners.services.accounting;

import com.ners.modules.Transactions;
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
public class TransactionRead {
  @Autowired
  HikariDataSource hds;

  public String readTransaction(Model model) {
    ArrayList<Transactions> transaction = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_TRANSACTIONS");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Transactions t = new Transactions();
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
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    return "transaction-table";
  }
}
