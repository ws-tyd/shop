package com.zhonghui.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhonghui.common.core.config.RedisUtils;
import com.zhonghui.common.core.enums.StatusEnum;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.DateMap;
import com.zhonghui.common.core.utils.JwtUtil;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.mapper.UserMapper;
import com.zhonghui.model.User;
import com.zhonghui.resp.RespUser;
import com.zhonghui.service.UserService;
import com.zhonghui.utils.SecuritUserUtil;
import com.zhonghui.utils.UserDetailUtil;
import com.zhonghui.vo.LoginVo;
import com.zhonghui.vo.RegisterVo;
import com.zhonghui.vo.UpdateUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.util.locale.provider.DateFormatProviderImpl;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.zhonghui.common.core.constant.FwConstants.USER_LOGIN_TOKEN;

/**
 * @description 用户信息-业务实现
 * @author zhonghui
 * @date 2020-11-29
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    private long time=24*60*60*1000*3;

    @Override
    @Transactional
    public FwResult register(RegisterVo registerVo) {
        User mobile=new User();
        mobile.setStatus(StatusEnum.ENABLE.getValue());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User mobileUser = this.getOne(wrapper.eq("user_name",registerVo.getLogin()));
        if(ObjectUtil.isNotNull(mobileUser)){
            throw new BadRequestException(422,"当前用户名已经存在");
        }
        mobile.setUserName(registerVo.getLogin());
        mobile.setPhone(registerVo.getLogin());
        mobile.setPassword(SecureUtil.md5(registerVo.getPassword()));
        mobile.setNickname(registerVo.getNickname());
        mobile.setType("user");
        mobile.setBalance(new BigDecimal(0));
        mobile.setDeleteFlag(0);
        userMapper.insert(mobile);
        User user = userMapper.selectById(mobile.getId());
        DateMap map = new DateMap();
        map.put("id",user.getId()).put("login",user.getUserName()).put("type",user.getType()).put("nickname",user.getNickname())
                .put("balance",user.getBalance()).put("created_at",user.getCreateTime()).put("updated_at",user.getUpdateTime());
        FwResult<Object> result = FwResult.ok();
        result.setData(map.getData());
        result.setStatus(201);
        return result;
    }

    @Override
    @Transactional
    public FwResult<String> updateUser(User user) {
        this.saveOrUpdate(user);
        return FwResult.ok();
    }

    @Override
    public FwResult getTokenUser(String token) {
//        System.out.println(token);
        if (redisUtils.hasKey(USER_LOGIN_TOKEN+token)) {
            String userJson = redisUtils.get(USER_LOGIN_TOKEN+token);
            User user = JSONUtil.toBean(userJson, User.class);
            return FwResult.ok(user);
        }else {
            return FwResult.failed(404,"notFound");
        }
    }

    @Override
    public FwResult queryUserAll() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("1",1);
        List<User> users = userMapper.selectList(wrapper);
        ArrayList<RespUser> maps = new ArrayList<>();
        users.forEach(user -> {
            maps.add(new RespUser(user));
        });
        if (users.size()==0)throw new BadRequestException(404,"没有任何用户信息");
        HashMap<Object, Object> map = new HashMap<>();
        map.put("items",maps);
        map.put("count",maps.size());
        map.put("skip",1);
        return FwResult.ok(map);
    }

    @Override
    public FwResult queryUserAllPage(PageUtil<User> pageUtil) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("1",1);
        IPage<User> userIPage = userMapper.selectPage(pageUtil, wrapper);
        if (userIPage.getSize()==0)throw new BadRequestException(404,"没有任何用户信息");
        return FwResult.ok(userIPage);
    }

    @Override
    public FwResult deleteUserById(Integer id) {
        User user = userMapper.selectById(id);
        if (user==null){
            throw new BadRequestException(404,"没有这个用户");
        }else {
            userMapper.deleteById(id);
        }
        return FwResult.ok(new RespUser(user));
    }

    @Override
    public FwResult updateUserById(UpdateUser user, Integer id) {
        User targetUser = userMapper.selectById(id);
        User updateUser = new SecuritUserUtil().getUser().getUser();
        if (targetUser==null){
            throw new BadRequestException(404,"没有这个用户");
        }else {
            user.setId(id);
            user.setUpdateUser(updateUser.getId());
            user.setUpdateTime(LocalDateTime.now());
            user.setCreateTime(targetUser.getCreateTime());
            userMapper.updateUser(user);
        }
        User target = userMapper.selectById(id);
        return FwResult.ok(new RespUser(target));
    }
    @Override
    public FwResult<Map<String, Object>> login(LoginVo loginVo) {
        User mobile=new User();
        mobile.setStatus(StatusEnum.ENABLE.getValue());
        System.out.println(loginVo);
        mobile.setUserName(loginVo.getUserName());
        User mobileUser = userMapper.selectOne(new QueryWrapper<User>().eq("user_name",loginVo.getUserName()));
        System.out.println(mobileUser);
        if(ObjectUtil.isNotNull(mobileUser)){
            System.out.println(loginVo);
            if(mobileUser.getPassword().equals(SecureUtil.md5(loginVo.getPassword()))){
                String key= UUID.randomUUID().toString();
                String userCache = JSONUtil.toJsonStr(mobileUser);
//                jwt begin
                HashMap<Object, Object> userInfo = new HashMap<>();
                userInfo.put("userName",mobileUser.getUserName());
                userInfo.put("password",mobileUser.getPassword());
                userInfo.put("role",mobileUser.getType());
                userInfo.put("user",mobileUser);
                String token1 = jwtUtil.getToken(userInfo);
                redisUtils.set(token1,userCache,3*24*60*60*1000);
//                jwt end
                log.info("用户登录=>{}",userCache);
                UserDetailUtil util = new UserDetailUtil(this);
                Authentication token = util.getUser(mobileUser.getUserName());
                SecurityContextHolder.getContext().setAuthentication(token);
                Map<String,Object> map=new HashMap<>();
//                map.put("Authorization",token1);
//                map.put("userInfo",mobileUser);
                map.put("expires_time",new Date(new Date().getTime()+time));
                map.put("token",token1);
                return FwResult.ok(map);
            }
        }
            throw new BadRequestException(401,"认证错误,账号或者密码错误");
    }

    @Override
    public FwResult logout(String token) {
        token=token.substring("Bearer ".length());
        User user = new SecuritUserUtil().getUser().getUser();
        if(StrUtil.isNotBlank(token)){
            redisUtils.del(token);
        }
        DateMap map = new DateMap();
        map.put("id",user.getId()).put("login",user.getUserName()).put("type",user.getType()).put("nickname",user.getNickname())
                .put("balance",user.getBalance()).put("created_at",user.getCreateTime()).put("updated_at",user.getUpdateTime());
        return FwResult.ok(map.getData());
    }
}
