package com.zhonghui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.Category;

public interface CategoryService extends IService<Category> {
    FwResult queryAllByTree();

    FwResult queryAllPage( PageUtil<Category> pageUtil);

    FwResult createCategory(Category category);

    FwResult deleteCategory(Integer id);

    FwResult updateCategory(Category category, Integer id);
}
