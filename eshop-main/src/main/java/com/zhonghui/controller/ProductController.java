package com.zhonghui.controller;

import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.Product;
import com.zhonghui.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product/getHot")
    public FwResult queryAllHotPro(@Valid @RequestBody PageUtil<Product> pageUtil){
        return productService.queryHotPro(pageUtil);
    }

    @PostMapping("/product")
    public FwResult queryAllPage(@Valid @RequestBody PageUtil<Product> pageUtil){
        return productService.queryAllPage(pageUtil);
    }

    @GetMapping("/product/{id}")
    public FwResult queryProById(@PathVariable("id")Integer id){
        return productService.queryProById(id);
    }

    @PutMapping("/product")
    @PreAuthorize("hasAuthority('staff')")
    public FwResult createProduct(@Valid @RequestBody Product product){
        return productService.createProduct(product);
    }
    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasAuthority('staff')")
    public FwResult deleteProduct(@PathVariable("id") Integer id){
        return productService.deleteProduct(id);
    }
    @PutMapping("/product/{id}")
    public FwResult updateProduct(){
        return FwResult.ok("暂未实现");
    }
}

