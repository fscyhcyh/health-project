package com.cyh.controller;

import com.cyh.constant.MessageConstant;
import com.cyh.entity.PageResult;
import com.cyh.entity.QueryPageBean;
import com.cyh.entity.Result;
import com.cyh.pojo.CheckGroup;
import com.cyh.service.CheckGroupService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds){
        try{
            checkGroupService.save(checkGroup,checkItemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findpage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

            return checkGroupService.findPage(queryPageBean);

    }

    @RequestMapping("/findbyid")
    public Result findById(Integer id){
        try{
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup );
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }

    @RequestMapping("/finditemsbygroupid")
    public Result findItemsByGroupId(Integer checkGroupId){
        try{
            List<Integer> checkItemIds = checkGroupService.findItemsByGroupId(checkGroupId);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds){
        try{
            System.out.println(checkGroup.getId());
            checkGroupService.edit(checkGroup,checkItemIds);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findall")
    public Result findAll() {
        try {
            List<CheckGroup> checkGroupList = checkGroupService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

}
