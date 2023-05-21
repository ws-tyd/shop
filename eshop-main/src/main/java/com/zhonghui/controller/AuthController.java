package com.zhonghui.controller;

import cn.hutool.json.JSONUtil;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.DateMap;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.User;
import com.zhonghui.resp.RespUser;
import com.zhonghui.service.UserService;
import com.zhonghui.utils.SecuritUserUtil;
import com.zhonghui.vo.LoginVo;
import com.zhonghui.vo.RegisterVo;
import com.zhonghui.vo.UpdateUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


/**
 * @description 用户信息-控制层
 * @author zhonghui
 * @date 2020-11-29
 */
@RestController
@Api(value = "用户信息")
@Slf4j
@Validated
public class AuthController {


    @Autowired
    private UserService userService;


    @ApiOperation("注册")
    @PostMapping("/register")
    public FwResult register(@RequestBody @Valid RegisterVo registerVo, HttpServletResponse response){
        response.setStatus(201);
        return userService.register(registerVo);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public FwResult re(@RequestBody @Valid LoginVo loginVo){
        return userService.login(loginVo);
    }

    @ApiOperation("获取token登录信息")
    @PostMapping("/getTokenUser")
    public FwResult getTokenUser(@RequestBody Map map){
        return userService.getTokenUser(map.get("Authorization").toString());
    }

    @ApiOperation("登出")
    @GetMapping("/logOut")
    public FwResult<User> logout(@RequestHeader(value = "Authorization",required = false) String token){
        return userService.logout(token);
    }
    @ApiOperation("登出")
    @GetMapping("/logout")
    public FwResult<User> logout2(@RequestHeader(value = "Authorization",required = false) String token){
        return userService.logout(token);
    }
    @ApiOperation("获取当前用户登录信息")
    @PostMapping("/userSession")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult userSession(){
        User user = new SecuritUserUtil().getUser().getUser();
        return FwResult.ok(user);
    }
    @ApiOperation("获取当前用户登录信息")
    @GetMapping("/session")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult userSession2(){
        User user = new SecuritUserUtil().getUser().getUser();
        DateMap map = new DateMap();
        map.put("id",user.getId()).put("login",user.getUserName()).put("type",user.getType()).put("nickname",user.getNickname())
                .put("balance",user.getBalance()).put("created_at",user.getCreateTime()).put("updated_at",user.getUpdateTime());
//        return FwResult.ok(user);
        return FwResult.ok(map.getData());
    }
    @ApiOperation("获取全部用户信息")
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('staff')")
    public FwResult queryUserAll(){
        return userService.queryUserAll();
    }

    @ApiOperation("获取全部用户信息分页")
    @PostMapping("/user")
    @PreAuthorize("hasAuthority('staff')")
    public FwResult queryUserAllPage(@Valid @RequestBody PageUtil<User> pageUtil){
        return userService.queryUserAllPage(pageUtil);
    }

    @ApiOperation("获取用户")
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('staff')")
    public FwResult queryById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        if (user==null){
            throw new BadRequestException(404,"没有这个用户");
        }
        return FwResult.ok(new RespUser(user));
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAuthority('staff')")
    public FwResult deleteUserById(@PathVariable("id") Integer id){
        return userService.deleteUserById(id);
    }
    @ApiOperation("修改用户信息")
    @PutMapping("/user/{id}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('user')")
    public FwResult updateUserById(@Valid @RequestBody UpdateUser user, @PathVariable("id") Integer id){
        return userService.updateUserById(user,id);
    }
}
