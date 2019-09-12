package com.ners.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subjects {
  private Integer subject_id;
  private String subject_name;
  private String subject_teacher;
  private String level;
  private Integer price;
}
