package com.zhonghui.controller;

import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.model.UserAddress;
import com.zhonghui.service.UserAddressService;
import com.zhonghui.vo.ReqAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/address")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult querAll(){
         return userAddressService.querAll();
    }

    @GetMapping("/address/{id}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult queryById( @PathVariable("id") int id){
        return userAddressService.queryById(id);
    }

    @PutMapping("/address")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult createAddress(@Valid @RequestBody ReqAddress reqAddress){
        return userAddressService.createAddress(reqAddress);
    }

    @PutMapping("/address/{id}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult updateAddress( @Valid @RequestBody ReqAddress reqAddress, @PathVariable("id") int id){
        return userAddressService.updateAddress(reqAddress,id);
    }

    @DeleteMapping("/address/{id}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('staff')")
    public FwResult deleteAddress( @PathVariable("id") int id){
        return userAddressService.deleteAddress(id);
    }
}
