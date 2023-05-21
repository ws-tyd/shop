package com.zhonghui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.mapper.CollectionMapper;
import com.zhonghui.mapper.ProductMapper;
import com.zhonghui.mapper.UserMapper;
import com.zhonghui.model.Collection;
import com.zhonghui.model.Product;
import com.zhonghui.model.User;
import com.zhonghui.service.CollectionService;
import com.zhonghui.utils.SecuritUserUtil;
import com.zhonghui.vo.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public FwResult addCollection(Integer pid) {
        Product product = productMapper.selectById(pid);
        User user = new SecuritUserUtil().getUser().getUser();
        if (product==null){
            throw new BadRequestException(404,"商品不存在");
        }
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getId());
        wrapper.eq("product_id",pid);
        Collection one = collectionMapper.selectOne(wrapper);
        if (one!=null){
            throw new BadRequestException(422,"不能重复添加收藏");
        }

        Collection collection = new Collection(0,user.getId(),product.getName(),product.getId(),product.getName(),0, LocalDateTime.now(),product.getMainImage());
        collectionMapper.insert(collection);
        return FwResult.ok(collectionMapper.selectById(collection.getId()));
    }

    @Override
    public FwResult deleteCollection(Integer pid) {
        Product product = productMapper.selectById(pid);
        if (product==null){
            throw new BadRequestException(404,"商品不存在");
        }
        User user = new SecuritUserUtil().getUser().getUser();
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getId());
        wrapper.eq("product_id",pid);
        Collection collection = collectionMapper.selectOne(wrapper);
        if (collection==null){
            throw new BadRequestException(422,"你不能取消没有收藏的商品");
        }
        collectionMapper.delete(wrapper);
        return FwResult.ok(collection);
    }

    @Override
    public FwResult queryByuserId(Integer userId) {
        User user = new SecuritUserUtil().getUser().getUser();
        User target = userMapper.selectById(userId);
        if (target==null){
            throw new BadRequestException(404,"用户不存在");
        }
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return FwResult.ok(collectionMapper.selectList(wrapper));
    }

    @Override
    public FwResult queryAll() {
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        User user = new SecuritUserUtil().getUser().getUser();
        wrapper.eq("1",1);
        wrapper.eq("user_id", user.getId());
        List<Collection> collections = collectionMapper.selectList(wrapper);
        return FwResult.ok(collections);
    }

    @Override
    public FwResult queryAllPage(PageUtil<Collection> pageUtil) {
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        User user = new SecuritUserUtil().getUser().getUser();
        wrapper.eq("1",1);
        wrapper.eq("user_id", user.getId());
        IPage<Collection> collectionIPage = collectionMapper.selectPage(pageUtil, wrapper);
        return FwResult.ok(collectionIPage);
    }
}
