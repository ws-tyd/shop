package com.zhonghui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.model.ImageInfo;

public interface ImageInfoService extends IService<ImageInfo> {
    ImageInfo uploadImage(String fileName,String url);

    FwResult queryAllImage(PageUtil<ImageInfo> pageUtil);

    FwResult deleteImage(Integer id);

    FwResult queryImage();
}
