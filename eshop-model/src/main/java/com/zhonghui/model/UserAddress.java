package com.zhonghui.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhonghui.vo.ReqAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {
  private Integer id;
  private Integer addressId;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;
  private Integer createUser;
  private Integer updateUser;
  private Integer deleteFlag;
  private Integer defaultFlag;
  private String receiveName;
  private String receivePhone;
  private String province;
  private String provinceCode;
  private String city;
  private String cityCode;
  private String area;
  private String areaCode;
  private String street;
  private String postalCode;
  private Integer addressLabel;

  public UserAddress(ReqAddress reqAddress) {
    this.createTime=LocalDateTime.now();
    this.updateTime=LocalDateTime.now();
    this.receiveName=reqAddress.getReceiveName();
    this.receivePhone=reqAddress.getReceivePhone();
    this.province=reqAddress.getProvince();
    this.provinceCode=reqAddress.getProvinceCode();
    this.city=reqAddress.getCity();
    this.cityCode=reqAddress.getCityCode();
    this.area=reqAddress.getArea();
    this.areaCode=reqAddress.getAreaCode();
    this.street=reqAddress.getStreet();
    this.postalCode=reqAddress.getPostalCode();
  }
}

/*****
 *
 *     "receiveName":"aaa",
 *     "receivePhone":"13712311231",
 *     "province":"北京",
 *     "provinceCode":"001",
 *     "city":"北京",
 *     "cityCode":"001001",
 *     "area":"北京",
 *     "areaCode":"001001001",
 *     "street":"1234567",
 *     "postalCode":"123321"
 * }
 */
