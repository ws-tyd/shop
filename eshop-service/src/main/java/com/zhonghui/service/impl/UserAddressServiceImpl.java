package com.zhonghui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.mapper.UserAddressMapper;
import com.zhonghui.model.UserAddress;
import com.zhonghui.service.UserAddressService;
import com.zhonghui.utils.SecuritUserUtil;
import com.zhonghui.vo.MyUserDetails;
import com.zhonghui.vo.ReqAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper,UserAddress> implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public FwResult querAll() {
        List<UserAddress> addresses=null;
        MyUserDetails user = new SecuritUserUtil().getUser();
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<UserAddress>().eq("1", 1);
        wrapper.select("id,create_user,create_time,address_id,default_flag,receive_name,receive_phone,province,province_code,city,city_code,area,area_code,street,postal_code");
        if (user.getUser()!=null) {
            if (user.getUser().getType().equals("user")){
                wrapper.eq("create_user",user.getUser().getId());
                addresses= userAddressMapper.selectList(wrapper);
            }else {
                addresses= userAddressMapper.selectList(wrapper);
            }
        }else {
            throw new BadRequestException(401,"验证失败");
        }
        return FwResult.ok(addresses,"successful");
    }

    @Override
    public FwResult createAddress(ReqAddress reqAddress) {
        MyUserDetails user = new SecuritUserUtil().getUser();
        UserAddress address = new UserAddress(reqAddress);
        address.setCreateUser(user.getUser().getId());
        userAddressMapper.insert(address);
        return FwResult.ok("successful");
    }

    @Override
    public FwResult updateAddress(ReqAddress reqAddress,int id) {
        UserAddress userAddress = userAddressMapper.selectById(id);
        if (userAddress==null) {
            throw new BadRequestException(404,"没有找到对应的地址信息");
        }
        UserAddress address = new UserAddress(reqAddress);
        address.setId(id);
        userAddressMapper.updateById(address);
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.select("id,create_time,address_id,default_flag,receive_name,receive_phone,province,province_code,city,city_code,area,area_code,street,postal_code");
        wrapper.eq("id",id);
        return FwResult.ok(userAddressMapper.selectOne(wrapper),"successful");
    }

    @Override
    public FwResult deleteAddress(int id) {
        UserAddress userAddress = userAddressMapper.selectById(id);
        if (userAddress==null) {
            throw new BadRequestException(404,"没有找到对应的地址信息");
        }
        userAddressMapper.deleteById(id);
        return FwResult.ok("successful");
    }

    @Override
    public FwResult queryById(int id) {
        UserAddress userAddress = userAddressMapper.selectById(id);
        if (userAddress==null) {
            throw new BadRequestException(404,"没有找到对应的地址信息");
        }
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.select("id,create_time,address_id,default_flag,receive_name,receive_phone,province,province_code,city,city_code,area,area_code,street,postal_code");
        wrapper.eq("id",id);
        return FwResult.ok(userAddressMapper.selectOne(wrapper),"successful");
    }
}
