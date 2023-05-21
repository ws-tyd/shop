package com.zhonghui.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

  private Integer id;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;
  private Integer createUser;
  private Integer updateUser;
  private Integer deleteFlag;
  private Integer userId;
  private Integer activityId;
  private String activityName;
  private Integer productId;
  private String productName;
  private String productSubtitle;
  private String productMainImage;
  private Integer quantity;
  private double productUnitPrice;
  private Boolean selected;
  private double productTotalPrice;

  public Cart(Product target, User user) {
    this.createTime=LocalDateTime.now();
    this.updateTime=LocalDateTime.now();
    this.createUser=user.getId();
    this.updateUser=user.getId();
    this.userId=user.getId();
    this.productId=target.getId();
    this.productName=target.getName();
    this.productMainImage=target.getMainImage();
    this.productSubtitle=target.getSubTitle();
    this.productUnitPrice=target.getPrice();
  }
}
