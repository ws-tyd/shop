package com.zhonghui.controller;

import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.Category;
import com.zhonghui.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/category")
    public FwResult queryAllBuTreeList(){
        return categoryService.queryAllByTree();
    }


    @PostMapping("/category")
    public FwResult queryAllPage(@Valid @RequestBody PageUtil<Category> pageUtil){
        return categoryService.queryAllPage(pageUtil);
    }

    @PutMapping("/category")
    public FwResult createCategory(@Valid @RequestBody Category category){
        return categoryService.createCategory(category);
    }
    @DeleteMapping("/category/{id}")
    public FwResult deleteCategory(@PathVariable("id") Integer id){
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/category/{id}")
    public FwResult updateCategory(@Valid @RequestBody Category category,@PathVariable("id") Integer id){
        return categoryService.updateCategory(category,id);
    }
}
