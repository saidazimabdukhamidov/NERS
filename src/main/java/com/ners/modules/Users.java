package com.ners.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
  private Integer user_id;
  private String full_name;
  private Integer role_id;
  private Integer Absence;
  private String group_id;
  private Integer dept;
  private String birth_date;
  private String phone_number;
  private String login;
  private String password;
  private Integer salary;
}
