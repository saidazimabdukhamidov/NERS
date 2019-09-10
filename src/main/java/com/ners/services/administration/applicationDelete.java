package com.ners.services.administration;

import com.ners.utils.dataBase;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.CallableStatement;
import java.sql.Connection;

@Service
public class applicationDelete {
  @Autowired
  HikariDataSource hds;

  public String deleteApplication(HttpServletRequest request) {
    Connection conn = null;
    CallableStatement cs = null;
    try {
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL APPLICANT_DELETE_P(?)}");
      cs.setInt(1, Integer.parseInt(request.getParameter("applicant_id")));
      cs.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dataBase.close(cs);
      dataBase.close(conn);
    }
    return "registration-table";
  }
}
