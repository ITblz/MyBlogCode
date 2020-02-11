package com.blz.controller;

import com.blz.domain.Article;
import com.blz.domain.Sort;
import com.blz.domain.Tag;
import com.blz.domain.Theme;
import com.blz.service.TagService;
import com.blz.utils.ParserJSONUtil;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping("/findAll")
    public @ResponseBody List<Tag> findAll(){
        List<Tag> tagList = tagService.findAll();
        return  tagList;
    }

    @RequestMapping("/showList.do")
    public @ResponseBody
    Map<String,Object> findGuestbookList(){
        List<Tag> tagList = tagService.findAll();
        Map<String,Object> map = new HashMap<>();
        map.put("total",tagList.size());
        map.put("rows",tagList);
        return map;
    }
    @RequestMapping("/add.do")
    public @ResponseBody
    Map<String,Object> add(@RequestBody Tag tag){
        long res = tagService.insert(tag);
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
    Map<String,Object> update(@RequestBody Tag tag){
        long res = tagService.update(tag);
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

        long res = tagService.batchDelete(idList);
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
