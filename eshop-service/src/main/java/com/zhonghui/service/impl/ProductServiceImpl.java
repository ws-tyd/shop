package com.zhonghui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.mapper.ProductMapper;
import com.zhonghui.model.Product;
import com.zhonghui.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public FwResult queryHotPro(PageUtil<Product> pageUtil) {
        QueryWrapper<Product> wrapper = new QueryWrapper<Product>().eq("hot_flag", 1);
        wrapper.select("id,create_time,product_id,category_id,name,sub_title,main_image,sub_images,status,price,stock,hot_flag");
        IPage hotList = productMapper.selectPage(pageUtil, wrapper);
        return FwResult.ok(hotList,"successful");
    }

    @Override
    public FwResult queryAllPage(PageUtil<Product> pageUtil) {
        QueryWrapper<Product> wrapper = new QueryWrapper<Product>().eq("1", 1);
        wrapper.select("id,create_time,product_id,category_id,name,sub_title,main_image,sub_images,status,price,stock,hot_flag");
        IPage<Product> page = productMapper.selectPage(pageUtil, wrapper);
        return FwResult.ok(page,"successful");
    }

    @Override
    public FwResult queryProById(Integer id) {
        Product product = productMapper.selectById(id);
        if (product==null){
            throw new BadRequestException(404,"没有该商品");
        }
        return FwResult.ok(product,"successful");
    }

    @Override
    public FwResult deleteProduct(Integer id) {
        Product product = productMapper.selectById(id);
        if (product==null){
            throw new BadRequestException(404,"没有该商品");
        }
        productMapper.deleteById(id);
        return FwResult.ok(product,"successful");
    }

    @Override
    public FwResult createProduct(Product product) {
        product.setCreateTime(LocalDateTime.now());
        productMapper.insert(product);
        return FwResult.ok(productMapper.selectById(product.getId()));
    }
}
