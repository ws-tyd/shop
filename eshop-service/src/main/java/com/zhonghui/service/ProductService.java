package com.zhonghui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.Product;

public interface ProductService extends IService<Product> {
    FwResult queryHotPro(PageUtil<Product> pageUtil);

    FwResult queryAllPage(PageUtil<Product> pageUtil);

    FwResult queryProById(Integer id);

    FwResult deleteProduct(Integer id);

    FwResult createProduct(Product product);
}
