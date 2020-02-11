package com.blz.controller;

import com.alibaba.fastjson.JSONObject;
import com.blz.domain.Article;
import com.blz.domain.Comment;
import com.blz.service.CommentService;
import com.blz.utils.ParserJSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/total")
    public @ResponseBody long findCommentTotal(){
        return commentService.findCommentTotal();
    }

    @RequestMapping("/insert")
    public @ResponseBody long insert(@RequestBody Comment comment){
        //System.out.println(comment);
        return commentService.insert(comment);
    }

    @RequestMapping("/updateLikeCount")
    public @ResponseBody Integer updateLikeCount(@RequestBody Comment comment){
        //System.out.println(comment);
        return commentService.updateLikeCount(comment);
    }

    @RequestMapping(value = "/showCommentList.do",method = RequestMethod.GET)
    public @ResponseBody
    Map<String,Object> showCommentList(@RequestParam(name = "pageSize",required = false) Integer pageSize,
                                       @RequestParam(name = "page",required = false) Integer page,
                                       @RequestParam(name = "sortOrder",required = false) String sortOrder){
        List<Comment> commentList = commentService.findAllComments();
        Map<String,Object> map = new HashMap<>();
        map.put("total",commentList.size());
        map.put("rows",commentList);
        return map;
    }
    @RequestMapping(value = "/batchUpdateCheckedFiled.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> batchUpdateCommentCheckedFiled(@RequestBody String ids){
        List<String> idList = ParserJSONUtil.parseBatchDeleteJSONIds(ids);
        long res = commentService.batchUpdateCommentCheckedFiled(idList);
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

        long res = commentService.batchDelete(idList);
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
