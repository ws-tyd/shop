package com.zhonghui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.Collection;

public interface CollectionService extends IService<Collection> {
    FwResult addCollection(Integer pid);

    FwResult deleteCollection(Integer pid);

    FwResult queryByuserId(Integer userId);

    FwResult queryAll();

    FwResult queryAllPage(PageUtil<Collection> pageUtil);
}
