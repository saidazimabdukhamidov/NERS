package com.ners.services.director;

import com.ners.utils.DataBase;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.CallableStatement;
import java.sql.Connection;

@Service
public class SubjectDelete {
  @Autowired
  HikariDataSource hds;

  public String deleteSubject(HttpServletRequest request) {
    Connection conn = null;
    CallableStatement cs = null;
    try {
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL SUBJECT_DELETE_P(?)}");
      cs.setInt(1, Integer.parseInt(request.getParameter("subject_id")));
      cs.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(cs);
      DataBase.close(conn);
    }
    return "subjects-table";
  }
}
