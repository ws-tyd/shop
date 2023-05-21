package com.zhonghui.controller;

import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.RechargeInfo;
import com.zhonghui.service.RechargeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RechargeInfoCoontroller {

    @Autowired
    private RechargeInfoService rechargeInfoService;

    @PostMapping("/recharge")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult createRechargeInfo(@Valid @RequestBody RechargeInfo rechargeInfo){
        return rechargeInfoService.createRechargeInfo(rechargeInfo);
    }

    @GetMapping("/recharges")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult queryRechargeAll(){
        return rechargeInfoService.queryRechargeAll();
    }

    @PostMapping("/recharges")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult queryRechargeAllByPage(@Valid @RequestBody PageUtil<RechargeInfo> pageUtil){
        return rechargeInfoService.queryRechargeAllByPage(pageUtil);
    }
}
