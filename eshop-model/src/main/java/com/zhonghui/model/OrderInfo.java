package com.zhonghui.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.vo.MyUserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {

  private Integer id;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;
  private Integer createUser;
  private Integer updateUser;
  private Integer deleteFlag;
  private String orderNo;
  private double payment;
  private Integer paymentType;
  private String paymentTypeDesc;
  private double postage;
  private Integer status;
  private String statusDesc;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime paymentTime;
  private Integer addressId;
  private String receiveName;
  private String receivePhone;
  private String province;
  private String city;
  private String area;
  private String street;
  private String postalCode;
  private Integer userId;

  public OrderInfo(UserAddress userAddress) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    MyUserDetails userDetails;
    if (principal instanceof MyUserDetails) {
      userDetails= (MyUserDetails) principal;
      this.createTime=LocalDateTime.now();
      this.updateTime=LocalDateTime.now();
      this.createUser=userDetails.getUser().getId();
      this.deleteFlag=0;
      this.postage=0;
      this.addressId=userAddress.getId();
      this.receiveName=userAddress.getReceiveName();
      this.receivePhone=userAddress.getReceivePhone();
      this.province=userAddress.getProvince();
      this.city=userAddress.getCity();
      this.area=userAddress.getArea();
      this.street=userAddress.getStreet();
      this.postalCode=userAddress.getPostalCode();
      this.userId=userDetails.getUser().getId();
    }else {
      throw new BadRequestException(401,"用户验证失败");
    }
  }
}
