package com.zhonghui.controller;

import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.Collection;
import com.zhonghui.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping("/collection/{pid}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('user')")
    public FwResult addCollection(@PathVariable("pid") Integer pid){
        return collectionService.addCollection(pid);
    }

    @DeleteMapping("/collection/{pid}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('user')")
    public FwResult deleteCollection(@PathVariable("pid") Integer pid){
        return collectionService.deleteCollection(pid);
    }

    @GetMapping("/collection/{userId}")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('user')")
    public FwResult queryByuserId(@PathVariable("userId") Integer userId){
        return collectionService.queryByuserId(userId);
    }

    @GetMapping("/collection")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('user')")
    public FwResult queryByuserId(){
        return collectionService.queryAll();
    }

    @PostMapping("/collection")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('user')")
    public FwResult queryByuserIdPage(@Valid @RequestBody PageUtil<Collection> pageUtil){
        return collectionService.queryAllPage(pageUtil);
    }

}
