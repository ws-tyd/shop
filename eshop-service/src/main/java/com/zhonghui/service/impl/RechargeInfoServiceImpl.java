package com.zhonghui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.mapper.RechargeInfoMapper;
import com.zhonghui.mapper.UserMapper;
import com.zhonghui.model.RechargeInfo;
import com.zhonghui.model.User;
import com.zhonghui.service.RechargeInfoService;
import com.zhonghui.utils.SecuritUserUtil;
import com.zhonghui.vo.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RechargeInfoServiceImpl extends ServiceImpl<RechargeInfoMapper, RechargeInfo> implements RechargeInfoService {


    @Autowired
    private RechargeInfoMapper rechargeInfoMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public FwResult createRechargeInfo(RechargeInfo rechargeInfo) {
        synchronized (this){
            SecuritUserUtil userUtil = new SecuritUserUtil();
            MyUserDetails userDetails = userUtil.getUser();
            if (userDetails==null)throw new BadRequestException(401,"用户信息错误，请重新登录再试");
            else {
                User user = userDetails.getUser();
                rechargeInfo.setCreateTime(LocalDateTime.now());
                rechargeInfo.setUpdateTime(LocalDateTime.now());
                rechargeInfo.setUserId(user.getId());
                rechargeInfoMapper.insert(rechargeInfo);
                user.setBalance(user.getBalance().add(rechargeInfo.getBalance()));
                userMapper.updateById(user);
            }
            return FwResult.ok();
        }
    }

    @Override
    public FwResult queryRechargeAll() {
        SecuritUserUtil securitUserUtil = new SecuritUserUtil();
        User user = securitUserUtil.getUser().getUser();
        QueryWrapper<RechargeInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("1",1);
        List<RechargeInfo> rechargeList;
        if (user.getType().equals("user")){
            wrapper.eq("user_id",user.getId());
            rechargeList= rechargeInfoMapper.selectList(wrapper);
        }else {
            rechargeList= rechargeInfoMapper.selectList(wrapper);
        }
        return FwResult.ok(rechargeList);
    }

    @Override
    public FwResult queryRechargeAllByPage(PageUtil<RechargeInfo> pageUtil) {
        SecuritUserUtil securitUserUtil = new SecuritUserUtil();
        User user = securitUserUtil.getUser().getUser();
        QueryWrapper<RechargeInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("1",1);
        IPage<RechargeInfo> infoIPage;
        if (user.getType().equals("user")){
            wrapper.eq("user_id",user.getId());
            infoIPage= rechargeInfoMapper.selectPage(pageUtil, wrapper);
        }else {
            infoIPage= rechargeInfoMapper.selectPage(pageUtil, wrapper);
        }
        return FwResult.ok(infoIPage);
    }
}
