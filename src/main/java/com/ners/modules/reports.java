package com.ners.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class reports {
  private Integer report_id;
  private String report_name;
  private Integer fund;
  private String created_by;
  private Date created_time;
}
