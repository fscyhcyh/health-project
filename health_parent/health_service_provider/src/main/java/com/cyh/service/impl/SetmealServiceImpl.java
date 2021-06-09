package com.cyh.service.impl;

import com.cyh.constant.RedisConstant;
import com.cyh.dao.SetmealDao;
import com.cyh.entity.PageResult;
import com.cyh.entity.QueryPageBean;
import com.cyh.pojo.Setmeal;
import com.cyh.service.SetmealService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${outputPath}")
    private String outputPath;

    @Override
    public void save(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.save(setmeal);
        Integer setmealId = setmeal.getId();

        if(checkgroupIds != null && checkgroupIds.length>0) {
            for (Integer checkGroupId : checkgroupIds) {
                Map map = new HashMap<String, Integer>();
                map.put("setmealId", setmealId);
                map.put("checkGroupId", checkGroupId);
                setmealDao.saveSetmealCheckGroup(map);
            }
        }
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());

        generateSetmealListHmtl();
        generateSetmealDetailHmtl();
    }

    public void generateSetmealListHmtl(){
        List<Setmeal> setmealList = setmealDao.selectAll();
        Map map = new HashMap();
        map.put("setmealList",setmealList);
        generateHtml("front_setmeal.ftl","front_setmeal.html",map);
    }

    public void generateSetmealDetailHmtl(){
        List<Setmeal> setmealList = setmealDao.selectAll();
        for(Setmeal setmeal:setmealList){
            Map map = new HashMap();
            map.put("setmeal",setmealDao.selectById(setmeal.getId()));
            generateHtml("front_setmeal_detail.ftl",
                    "setmeal_detail_"+ setmeal.getId()+ ".html",
                    map);
        }
    }

    public void generateHtml(String templateName, String htmlName, Map map){
        Writer writer = null;
        try {
            writer = new FileWriter(new File(outputPath+"/"+htmlName));
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
            template.process(map,writer);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        } finally {
            if(writer!=null){
                try {
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        List<Setmeal> setmealList = setmealDao.selectByCondition(queryPageBean.getQueryString());
        PageInfo pageInfo = new PageInfo(setmealList);

        System.out.println(queryPageBean.getQueryString());
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo);
        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getList());

        return pageResult;
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.selectAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.selectById(id);
    }

    @Override
    public List<Map<String, Object>> getSetmealCount() {
        return setmealDao.getSetmealCount();
    }
}
