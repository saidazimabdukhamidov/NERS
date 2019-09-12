package com.ners.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Students {
  private Integer student_id;
  private String full_name;
  private String group_number;
  private String phone_number;
  private String birth_date;
  private String subject_study;
  private String chosen_time;
  private String days_study;
  private Integer dept;
  private String teacher_name;
  private Integer subject_price;
}
