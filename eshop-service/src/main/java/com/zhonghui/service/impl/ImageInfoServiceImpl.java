package com.zhonghui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.mapper.ImageInfoMapper;
import com.zhonghui.model.ImageInfo;
import com.zhonghui.resp.RespImage;
import com.zhonghui.service.ImageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ImageInfoServiceImpl extends ServiceImpl<ImageInfoMapper, ImageInfo> implements ImageInfoService {

    @Autowired
    private ImageInfoMapper imageInfoMapper;

    @Override
    public ImageInfo uploadImage(String fileName,String url) {
        ImageInfo imageInfo = new ImageInfo(0, LocalDateTime.now(),LocalDateTime.now(),url,fileName);
        imageInfoMapper.insert(imageInfo);
        return imageInfoMapper.selectById(imageInfo.getId());
    }

    @Override
    public FwResult queryAllImage(PageUtil<ImageInfo> pageUtil) {
        QueryWrapper<ImageInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("1","1");
        IPage<ImageInfo> imageInfoIPage = imageInfoMapper.selectPage(pageUtil, wrapper);
        return FwResult.ok(imageInfoIPage,"succefful");
    }

    @Override
    public FwResult deleteImage(Integer id) {
        ImageInfo imageInfo = imageInfoMapper.selectById(id);
        if (imageInfo==null)throw new BadRequestException(404,"图片未找到");
        else {
            String fileName = imageInfo.getTitle();
            File file = new File(imageInfo.getUrl());
            if (file.exists()) {
                file.delete();
            }
            imageInfoMapper.deleteById(id);
        }
        return FwResult.ok(new RespImage(imageInfo));
    }

    @Override
    public FwResult queryImage() {
        QueryWrapper<ImageInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("1","1");
        List<ImageInfo> imageInfos = imageInfoMapper.selectList(wrapper);
        return FwResult.ok(imageInfos);
    }
}
