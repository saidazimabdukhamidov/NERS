package com.ners.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
  private Integer transaction_id;
  private String send_by;
  private String reason;
  private String send_to;
  private Integer fund;
  private Date send_time;
}
