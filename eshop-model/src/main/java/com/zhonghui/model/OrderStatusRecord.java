package com.zhonghui.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusRecord {

  private Integer id;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
  private String orderNo;
  private String orderDetailNo;
  private Integer productId;
  private String productName;
  private Integer status;
  private String statusDesc;
  private Integer productCount;
  private Double productPrice;

}
