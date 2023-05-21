package com.zhonghui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.RechargeInfo;

public interface RechargeInfoService extends IService<RechargeInfo> {
    FwResult createRechargeInfo(RechargeInfo rechargeInfo);

    FwResult queryRechargeAll();

    FwResult queryRechargeAllByPage(PageUtil<RechargeInfo> pageUtil);
}
