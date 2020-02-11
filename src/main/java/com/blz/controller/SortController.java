package com.blz.controller;

import com.blz.domain.Article;
import com.blz.domain.Sort;
import com.blz.domain.Theme;
import com.blz.service.SortService;
import com.blz.utils.ParserJSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("sort")
public class SortController {

    @Autowired
    private SortService sortService;

    @RequestMapping("/findAll")
    public @ResponseBody List<Sort> findAll(){
        return sortService.findAll();
    }

    @RequestMapping("/showList.do")
    public @ResponseBody
    Map<String,Object> findGuestbookList(){
        List<Sort> sortList = sortService.findAll();
        Map<String,Object> map = new HashMap<>();
        map.put("total",sortList.size());
        map.put("rows",sortList);
        return map;
    }
    @RequestMapping("/add.do")
    public @ResponseBody
    Map<String,Object> add(@RequestBody Sort sort){
        long res = sortService.insert(sort);
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
    Map<String,Object> update(@RequestBody Sort sort){
        long res = sortService.update(sort);
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

        List<String> idList = ParserJSONUtil.parseBatchDeleteJSONIds(ids);

        long res = sortService.batchDelete(idList);
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
