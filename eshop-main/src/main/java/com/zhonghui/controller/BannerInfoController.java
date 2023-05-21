package com.zhonghui.controller;

import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.BannerInfo;
import com.zhonghui.service.BannerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class BannerInfoController {

    @Autowired
    private BannerInfoService bannerInfoService;


    @GetMapping("/banner")
    public FwResult queryAllBannerInfos(){
        return bannerInfoService.queryAllBannerInfos();
    }
    @PostMapping("/banner")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('user')")
    public FwResult queryAllBannerInfosPage(@Valid @RequestBody PageUtil<BannerInfo> pageUtil){
        return bannerInfoService.queryAllBannerInfosPage(pageUtil);
    }
    @PutMapping("/banner")
    @PreAuthorize("hasAuthority('staff')")
    public FwResult createBanner(@Valid @RequestBody BannerInfo bannerInfo){
        return bannerInfoService.createBanner(bannerInfo);
    }

    @PutMapping("/banner/{id}")
    @PreAuthorize("hasAuthority('staff')")
    public FwResult updateBanner(@Valid @RequestBody BannerInfo bannerInfo,@PathVariable("id") Integer id){
        return bannerInfoService.updateBanner(bannerInfo,id);
    }
    @DeleteMapping("/banner/{id}")
    @PreAuthorize("hasAuthority('staff')")
    public FwResult deleteBanner(@PathVariable("id") Integer id){
        return bannerInfoService.deleteBanner(id);
    }
}
