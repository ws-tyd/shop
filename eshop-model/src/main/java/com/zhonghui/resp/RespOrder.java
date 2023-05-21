package com.zhonghui.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhonghui.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespOrder {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    @JsonIgnore
    private Integer createUser;
    @JsonIgnore
    private Integer updateUser;
    @JsonIgnore
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
    private List<Product> details;
}
