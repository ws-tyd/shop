package com.zhonghui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.mapper.BannerInfoMapper;
import com.zhonghui.model.BannerInfo;
import com.zhonghui.service.BannerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BannerInfoServiceImpl extends ServiceImpl<BannerInfoMapper, BannerInfo> implements BannerInfoService {

    @Autowired
    private BannerInfoMapper bannerInfoMapper;

    @Override
    public FwResult queryAllBannerInfos(){
        QueryWrapper<BannerInfo> wrapper = new QueryWrapper<BannerInfo>().eq("1", "1");
        List<BannerInfo> bannerInfos = bannerInfoMapper.selectList(wrapper);
        return FwResult.ok(bannerInfos,"successful");
    }

    @Override
    public FwResult createBanner(BannerInfo bannerInfo) {
        bannerInfo.setCreateTime(LocalDateTime.now());
        bannerInfo.setUpdateTime(LocalDateTime.now());
        bannerInfoMapper.insert(bannerInfo);
        return FwResult.ok(bannerInfoMapper.selectById(bannerInfo.getId()));
    }

    @Override
    public FwResult updateBanner(BannerInfo bannerInfo, Integer id) {
        BannerInfo info = bannerInfoMapper.selectById(id);
        if (info==null){
            throw  new BadRequestException(404,"banner not found ");
        }else {
            bannerInfo.setUpdateTime(LocalDateTime.now());
            bannerInfo.setId(id);
            bannerInfoMapper.updateById(bannerInfo);
            return FwResult.ok(bannerInfoMapper.selectById(id));
        }
    }

    @Override
    public FwResult deleteBanner(Integer id) {
        BannerInfo info = bannerInfoMapper.selectById(id);
        if (info==null){
            throw  new BadRequestException(404,"banner not found ");
        }else {
            bannerInfoMapper.deleteById(id);
            return FwResult.ok();
        }
    }

    @Override
    public FwResult queryAllBannerInfosPage(PageUtil<BannerInfo> pageUtil) {
        QueryWrapper<BannerInfo> wrapper = new QueryWrapper<BannerInfo>().eq("1", "1");
        IPage<BannerInfo> iPage = bannerInfoMapper.selectPage(pageUtil, wrapper);
        return FwResult.ok(iPage,"successful");
    }

}
