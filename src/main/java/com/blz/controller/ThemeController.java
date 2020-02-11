package com.blz.controller;

import com.blz.dao.ThemeDao;
import com.blz.domain.*;
import com.blz.service.ThemeService;
import com.blz.utils.ParserJSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller()
@RequestMapping("theme")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @RequestMapping("/findAll")
    public @ResponseBody List<Theme> findAll(){
        return  themeService.findAll();
    }

    @RequestMapping("/showList.do")
    public @ResponseBody
    Map<String,Object> findGuestbookList(){
        List<Theme> themeList = themeService.findAll();
        Map<String,Object> map = new HashMap<>();
        map.put("total",themeList.size());
        map.put("rows",themeList);
        return map;
    }
    @RequestMapping("/add.do")
    public @ResponseBody
    Map<String,Object> add(@RequestBody Theme theme){
        long res = themeService.insert(theme);
        Map<String,Object> map = new HashMap<>();
        if (res == -1){
            map.put("status","error");
            map.put("msg","添加失败！");
        }else {
            map.put("status","success");
            map.put("msg","添加成功！");
            map.put("id",res);
        }
        return map;
    }
    @RequestMapping("/update.do")
    public @ResponseBody
    Map<String,Object> update(@RequestBody Theme theme){
        long res = themeService.update(theme);
        Map<String,Object> map = new HashMap<>();
        if (res == -1){
            map.put("status","error");
            map.put("msg","更新失败！");
        }else {
            map.put("status","success");
            map.put("msg","更新成功！");
        }
        return map;
    }
    @RequestMapping(value = "/batchDelete.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> batchDelete(@RequestBody String ids){
        System.out.println(ids);
        List<String> idList = ParserJSONUtil.parseBatchDeleteJSONIds(ids);

        long res = themeService.batchDelete(idList);
        System.out.println(res);
        Map<String,Object> map = new HashMap<>();
        if (res == idList.size()){
            map.put("status","success");
            map.put("msg","全部删除成功！");
        }else if (res == -1){
            map.put("status","error");
            map.put("msg","删除失败！");
        }
        else {
            map.put("status","exception");
            map.put("msg","删除有异常！");
        }
        map.put("rows",res);
        return map;
    }

}