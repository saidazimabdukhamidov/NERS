package com.ners.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Applicant {
  private Integer applicant_id;
  private String full_name;
  private String birth_date;
  private String study_place;
  private String subject;
  private String chosen_time;
  private String parent_time;
  private String address;
  private String phone_number;
  private String found_where;
  private String state;
}
