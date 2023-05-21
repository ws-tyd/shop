package com.zhonghui.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespPro {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private Integer productId;
    private Integer categoryId;
    private String name;
    private String subTitle;
    private String mainImage;
    private String subImages;
    private Integer status;
    private double price;
    private Integer stock;
    private Integer hotFlag;
}
