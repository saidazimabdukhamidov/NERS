package com.ners.services.administration;

import com.google.gson.JsonObject;
import com.ners.modules.Time;
import com.ners.modules.*;
import com.ners.utils.DataBase;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;

@Service
public class ApplicationCrud {
  @Autowired
  HikariDataSource hds;

  public String insertApplication(HttpServletRequest request) {
    JsonObject json = new JsonObject();
    Connection conn = null;
    CallableStatement cs = null;
    try {
      String full_name = request.getParameter("full_name");
      String birth_date = request.getParameter("birth_date");
      String study_place = request.getParameter("study_place");
      String subject = request.getParameter("subject");
      String chosen_time = request.getParameter("chosen_time");
      String parent_name = request.getParameter("parent_name");
      String address = request.getParameter("address");
      String phone_number = request.getParameter("phone_number");
      String found_where = request.getParameter("found_where");
      String state = request.getParameter("state");
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL NERS.APPLICANT_INSERT_P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
      cs.setString(1, full_name);
      cs.setString(2, birth_date);
      cs.setString(3, study_place);
      cs.setString(4, subject);
      cs.setString(5, chosen_time);
      cs.setString(6, parent_name);
      cs.setString(7, address);
      cs.setString(8, phone_number);
      cs.setString(9, found_where);
      cs.setString(10, state);
      int result = cs.executeUpdate();
      if (result > 0) {
        json.addProperty("location", "/application-list");
      } else {
        json.addProperty("msg", "Xato!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(cs);
      DataBase.close(conn);
    }
    return json.toString();
  }

  public String readInsertApplication(Model model) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ArrayList<Dept> depts = new ArrayList<>();
    ArrayList<Subjects> subjects = new ArrayList<>();
    ArrayList<Time> times = new ArrayList<>();
    ArrayList<Found> founds = new ArrayList<>();
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_DEPT");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Dept d = new Dept();
        d.setState_id(rs.getInt("state_id"));
        d.setState_type(rs.getString("state_type"));
        depts.add(d);
      }
      model.addAttribute("depts", depts);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_SUBJECTS");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Subjects s = new Subjects();
        s.setSubject_id(rs.getInt("subject_id"));
        s.setSubject_name(rs.getString("subject_name"));
        s.setSubject_teacher(rs.getString("subject_teacher"));
        s.setLevel(rs.getString("level"));
        s.setPrice(rs.getInt("price"));
        subjects.add(s);
      }
      model.addAttribute("subjects", subjects);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.CLASS_TIME");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Time t = new Time();
        t.setSubject_id(rs.getInt("subject_id"));
        t.setSubject_time(rs.getString("subject_time"));
        times.add(t);
      }
      model.addAttribute("times", times);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.FOUND_WHERE");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Found f = new Found();
        f.setType_id(rs.getInt("type_id"));
        f.setType_name(rs.getString("type_name"));
        founds.add(f);
      }
      model.addAttribute("founds", founds);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    return "application-insert";
  }

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
        a.setParent_name(rs.getString("parent_name"));
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

  public String readArchive(Model model) {
    ArrayList<Applicant> applicants = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.ARCHIVE");
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
        a.setParent_name(rs.getString("parent_name"));
        a.setAddress(rs.getString("address"));
        a.setPhone_number(rs.getString("phone_number"));
        a.setFound_where(rs.getString("found_where"));
        a.setState(rs.getString("state"));
        applicants.add(a);
      }
      model.addAttribute("archives", applicants);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    return "archive-table";
  }

  public String editApplication(HttpServletRequest request, Model model) {
    ArrayList<Applicant> applicants = new ArrayList<>();
    ArrayList<Dept> depts = new ArrayList<>();
    ArrayList<Subjects> subjects = new ArrayList<>();
    ArrayList<Time> times = new ArrayList<>();
    ArrayList<Found> founds = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      conn = hds.getConnection();
      int applicant_id = Integer.parseInt(request.getParameter("applicant_id"));
      ps = conn.prepareStatement("SELECT * FROM NERS.APPLICANT WHERE APPLICANT_ID = ?");
      ps.setInt(1, applicant_id);
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
        a.setParent_name(rs.getString("parent_name"));
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
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.FOUND_WHERE");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Found f = new Found();
        f.setType_id(rs.getInt("type_id"));
        f.setType_name(rs.getString("type_name"));
        founds.add(f);
      }
      model.addAttribute("founds", founds);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_SUBJECTS");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Subjects s = new Subjects();
        s.setSubject_id(rs.getInt("subject_id"));
        s.setSubject_name(rs.getString("subject_name"));
        s.setSubject_teacher(rs.getString("subject_teacher"));
        s.setLevel(rs.getString("level"));
        s.setPrice(rs.getInt("price"));
        subjects.add(s);
      }
      model.addAttribute("subjects", subjects);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.CLASS_TIME");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        com.ners.modules.Time t = new Time();
        t.setSubject_id(rs.getInt("subject_id"));
        t.setSubject_time(rs.getString("subject_time"));
        times.add(t);
      }
      model.addAttribute("times", times);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    try {
      conn = hds.getConnection();
      ps = conn.prepareStatement("SELECT * FROM NERS.MAIN_DEPT");
      ps.execute();
      rs = ps.getResultSet();
      while (rs.next()) {
        Dept d = new Dept();
        d.setState_id(rs.getInt("state_id"));
        d.setState_type(rs.getString("state_type"));
        depts.add(d);
      }
      model.addAttribute("depts", depts);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DataBase.close(rs);
      DataBase.close(ps);
      DataBase.close(conn);
    }
    return "application-update";
  }

  public String updateApplication(HttpServletRequest request) {
    JsonObject json = new JsonObject();
    Connection conn = null;
    CallableStatement cs = null;
    try {
      int applicant_id = Integer.parseInt(request.getParameter("applicant_id"));
      String full_name = request.getParameter("full_name");
      String birth_date = request.getParameter("birth_date");
      String study_place = request.getParameter("study_place");
      String subject = request.getParameter("subject");
      String chosen_time = request.getParameter("chosen_time");
      String parent_name = request.getParameter("parent_name");
      String address = request.getParameter("address");
      String phone_number = request.getParameter("phone_number");
      String found_where = request.getParameter("found_where");
      String state = request.getParameter("state");
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL NERS.APPLICANT_UPDATE_P(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
      cs.setInt(1, applicant_id);
      cs.setString(2, full_name);
      cs.setString(3, birth_date);
      cs.setString(4, study_place);
      cs.setString(5, subject);
      cs.setString(6, chosen_time);
      cs.setString(7, parent_name);
      cs.setString(8, address);
      cs.setString(9, phone_number);
      cs.setString(10, found_where);
      cs.setString(11, state);
      int result = cs.executeUpdate();
      if (result > 0) {
        json.addProperty("location", "/application-list");
      } else {
        json.addProperty("msg", "Xato!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(cs);
      DataBase.close(conn);
    }
    return json.toString();
  }

  public String deleteApplication(HttpServletRequest request) {
    Connection conn = null;
    CallableStatement cs = null;
    try {
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL NERS.APPLICANT_ARCHIVE_P(?)}");
      cs.setInt(1, Integer.parseInt(request.getParameter("applicant_id")));
      cs.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(cs);
      DataBase.close(conn);
    }
    return "redirect:/application-list";
  }

  public String deleteArchive(HttpServletRequest request) {
    Connection conn = null;
    CallableStatement cs = null;
    try {
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL NERS.APPLICANT_DELETE_P(?)}");
      cs.setInt(1, Integer.parseInt(request.getParameter("applicant_id")));
      cs.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(cs);
      DataBase.close(conn);
    }
    return "redirect:/archive-table";
  }

  public String restoreArchive(HttpServletRequest request) {
    Connection conn = null;
    CallableStatement cs = null;
    try {
      conn = hds.getConnection();
      cs = conn.prepareCall("{CALL NERS.APPLICANT_RESTORE_P(?)}");
      cs.setInt(1, Integer.parseInt(request.getParameter("applicant_id")));
      cs.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DataBase.close(cs);
      DataBase.close(conn);
    }
    return "redirect:/archive-table";
  }
}
