package com.blz.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blz.domain.Guestbook;
import com.blz.service.GuestbookService;
import com.blz.utils.ParserJSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("guestbook")
public class GuestbookController {

    @Autowired
    private GuestbookService guestbookService;

    @RequestMapping("/list/{offset}/{rows}")
    public @ResponseBody List<Guestbook> findGuestbookList(@PathVariable("offset") Integer offset, @PathVariable("rows")Integer rows){
        return guestbookService.findGuestbookListByChecked(offset,rows);
    }

    @RequestMapping("/insert")
    public @ResponseBody Integer insert(@RequestBody Guestbook guestbook){
        //System.out.println(guestbook);
        return guestbookService.insert(guestbook);
    }

    @RequestMapping("/showGuestbookList.do")
    public @ResponseBody Map<String,Object> findGuestbookList(){
        List<Guestbook> guestbookList = guestbookService.findGuestbookList(1,1000);
        Map<String,Object> map = new HashMap<>();
        map.put("total",guestbookList.size());
        map.put("rows",guestbookList);
        return map;
    }

    /**
     * 批量更新留言的是否通过审核字段
     * @return
     */
    @RequestMapping(value = "/batchUpdateCheckedFiled.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> batchUpdateGuestbookCheckedFiled(@RequestBody String ids){
        List<String> idList = ParserJSONUtil.parseBatchDeleteJSONIds(ids);
        long res = guestbookService.batchUpdateGuestbookCheckedFiled(idList);
        Map<String,Object> map = new HashMap<>();
        if (res == idList.size()){
            map.put("status","success");
            map.put("msg","全部更新成功！");
        }else if (res == -1){
            map.put("status","error");
            map.put("msg","更新失败！");
        }
        else {
            map.put("status","exception");
            map.put("msg","更新有异常！");
        }
        map.put("rows",res);
        return map;
    }

    @RequestMapping(value = "/batchDelete.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> batchDelete(@RequestBody String ids){

        List<String> idList = ParserJSONUtil.parseBatchDeleteJSONIds(ids);

        long res = guestbookService.batchDelete(idList);
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
