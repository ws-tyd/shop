package com.zhonghui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.result.FwResult;
import com.zhonghui.common.core.utils.PageUtil;
import com.zhonghui.mapper.CategoryMapper;
import com.zhonghui.model.Category;
import com.zhonghui.model.User;
import com.zhonghui.resp.RespCategory;
import com.zhonghui.service.CategoryService;
import com.zhonghui.utils.SecuritUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public FwResult queryAllByTree(){
        List<RespCategory> all = categoryMapper.queryAllByTree();
        all=toTreeList(all);
        return FwResult.ok(all,"successful");
    }

    @Override
    public FwResult queryAllPage(PageUtil<Category> pageUtil) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("1",1);
        IPage<Category> selectPage = categoryMapper.selectPage(pageUtil, wrapper);
        return FwResult.ok(selectPage,"successful");
    }

    @Override
    public FwResult createCategory(Category category) {
        User user = new SecuritUserUtil().getUser().getUser();
        if (category.getParentId()!=0){
            Category parent = categoryMapper.selectById(category.getParentId());
            if (parent==null)throw new BadRequestException(404,"插入节点失败，不存在这个父节点");
        }
        category.setCreateTime(LocalDateTime.now());
        category.setCreateUser(user.getId());
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(user.getId());
        categoryMapper.insert(category);
        return FwResult.ok();
    }

    @Override
    public FwResult deleteCategory(Integer id) {

        Category category = categoryMapper.selectById(id);
        if (category==null)throw new BadRequestException(404,"没有这个菜单");
        else {
            categoryMapper.deleteById(id);
            return FwResult.ok();
        }
    }

    @Override
    public FwResult updateCategory(Category category, Integer id) {
        Category target = categoryMapper.selectById(id);
        User user = new SecuritUserUtil().getUser().getUser();
        if (target==null)throw new BadRequestException(404,"没有这个菜单");
        else {
            target.setParentId(category.getParentId());
            target.setUpdateUser(user.getId());
            target.setUpdateTime(LocalDateTime.now());
            categoryMapper.updateById(target);
            return FwResult.ok(categoryMapper.selectById(id));
        }
    }

    private List<RespCategory> toTreeList(List<RespCategory> all) {
        ArrayList<RespCategory> treeList = new ArrayList<>();

        for (RespCategory category : all) {
            if (category.getParentId()==0) {
                treeList.add(category);
                category.setTreeList(getChildTreeList(category.getId(),all));
            }
        }

        return treeList;
    }

    private List<RespCategory> getChildTreeList(Integer pid, List<RespCategory> all) {
        List<RespCategory> treeList = new ArrayList<>();
        for (RespCategory category : all) {
            if (category.getParentId()==pid) {
                treeList.add(category);
                category.setTreeList(getChildTreeList(category.getId(),all));
            }
        }
        return treeList;
    }
}
