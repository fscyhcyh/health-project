package com.cyh.service.impl;

import com.cyh.dao.CheckItemDao;
import com.cyh.entity.PageResult;
import com.cyh.entity.QueryPageBean;
import com.cyh.pojo.CheckItem;
import com.cyh.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public boolean save(CheckItem checkItem) {
        return checkItemDao.save(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        String parameter = queryPageBean.getQueryString();
        List<CheckItem> checkItemList = checkItemDao.selectByCondition(parameter);
        System.out.println(parameter);
        PageInfo pageInfo = new PageInfo(checkItemList);


        System.out.println(pageInfo.getTotal());
        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getList());
//        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public void deleteById(int id) {
        long count = checkItemDao.countByCheckItemId(id);
        if(count>0){
            throw new RuntimeException();
        }else{
            checkItemDao.deleteById(id);
        }
    }

    @Override
    public CheckItem findById(int id) {
        return checkItemDao.selectById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.selectAll();
    }


}
