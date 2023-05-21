package com.zhonghui.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqAddress {

    @NotBlank(message = "收貨人不能爲空")
   private String  receiveName;
    @NotBlank(message = "收貨人电话不能爲空")
   private String  receivePhone;
    @NotBlank(message = "省份不能爲空")
   private String  province;
    @NotBlank(message = "省份编码不能爲空")
   private String  provinceCode;
    @NotBlank(message = "城市不能爲空")
   private String  city;
    @NotBlank(message = "城市编码不能爲空")
   private String  cityCode;
    @NotBlank(message = "地区不能爲空")
   private String  area;
    @NotBlank(message = "地区编码不能爲空")
   private String  areaCode;
    @NotBlank(message = "详细地址不能爲空")
   private String  street;
    @NotBlank(message = "邮编不能爲空")
   private String  postalCode;
}
