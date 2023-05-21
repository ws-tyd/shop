package com.zhonghui.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.User;
import com.zhonghui.vo.UpdateUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description 用户信息-Mapper
 * @author zhonghui
 * @date 2020-11-29
 */
@Mapper
@Repository
public interface UserMapper  extends BaseMapper<User> {
    int updateUser(UpdateUser user);

    @Select("select * from user where  delete_flag=0  limit #{skip},#{take} ")
    List<User> selectUserByPage(@Param("skip") Integer skpi,@Param("take") Integer take);
}
