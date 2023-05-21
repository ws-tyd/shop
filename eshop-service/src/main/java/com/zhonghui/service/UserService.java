package com.zhonghui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.User;
import com.zhonghui.vo.LoginVo;
import com.zhonghui.vo.RegisterVo;
import com.zhonghui.vo.UpdateUser;

import java.util.Map;

/**
 * @description 用户信息-业务接口
 * @author zhonghui
 * @date 2020-11-29
 */
public interface UserService extends IService<User> {

    FwResult register(RegisterVo user);

    FwResult<Map<String, Object>> login(LoginVo loginVo);

    FwResult<User> logout(String token);


    FwResult<String> updateUser(User user);

    FwResult getTokenUser(String token);

    FwResult queryUserAll();

    FwResult queryUserAllPage(PageUtil<User> pageUtil);

    FwResult deleteUserById(Integer id);

    FwResult updateUserById(UpdateUser user, Integer id);
}
