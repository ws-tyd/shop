package com.zhonghui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.model.UserAddress;
import com.zhonghui.vo.ReqAddress;


public interface UserAddressService extends IService<UserAddress> {
    FwResult querAll();

    FwResult createAddress(ReqAddress reqAddress);

    FwResult updateAddress(ReqAddress reqAddress,int id);

    FwResult deleteAddress(int id);

    FwResult queryById(int id);
}
