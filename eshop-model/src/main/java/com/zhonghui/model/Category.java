package com.zhonghui.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
  private Integer id;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Integer createUser;
  private Integer updateUser;
  private Integer deleteFlag;
  @NotNull(message = "父级节点是必要的，顶级节点为零")
  @Range(min = 0,message = "节点范围错误")
  private Integer parentId;
  @NotNull(message = "节点名字不能少")
  private String name;
  private Integer status;
  private Integer sortOrder;
  private Integer hotFlag;

}
