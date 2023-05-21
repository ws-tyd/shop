package com.zhonghui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.BannerInfo;


public interface BannerInfoService extends IService<BannerInfo> {
    FwResult queryAllBannerInfos();

    FwResult createBanner(BannerInfo bannerInfo);

    FwResult updateBanner(BannerInfo bannerInfo, Integer id);

    FwResult deleteBanner(Integer id);

    FwResult queryAllBannerInfosPage(PageUtil<BannerInfo> pageUtil);
}
