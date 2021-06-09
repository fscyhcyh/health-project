package com.cyh.service.impl;

import com.cyh.dao.CheckGroupDao;
import com.cyh.entity.PageResult;
import com.cyh.entity.QueryPageBean;
import com.cyh.pojo.CheckGroup;
import com.cyh.service.CheckGroupService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void save(CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupDao.save(checkGroup);
        Integer checkGroupId = checkGroup.getId();

        if(checkItemIds != null && checkItemIds.length>0){
            for(Integer checkItemId: checkItemIds){
                Map map = new HashMap<>();
                map.put("checkGroupId",checkGroupId);
                map.put("checkItemId",checkItemId);

                checkGroupDao.saveCheckGroupCheckItem(map);
            }
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<CheckGroup> checkGroupList = checkGroupDao.selectByCondition(queryPageBean.getQueryString());
        PageInfo pageInfo = new PageInfo(checkGroupList);

        System.out.println(queryPageBean.getQueryString());
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo);

        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getList());
        return pageResult;
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.selectById(id);
    }

    @Override
    public List<Integer> findItemsByGroupId(Integer id) {
        return checkGroupDao.selectItemsByGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupDao.update(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        checkGroupDao.deleteCheckGroupCheckItemsByGroupId(checkGroupId);
        for(Integer checkItemId: checkItemIds){
            Map map = new HashMap<>();
            map.put("checkGroupId",checkGroupId);
            map.put("checkItemId",checkItemId);
            checkGroupDao.saveCheckGroupCheckItem(map);
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.selectAll();
    }
}
