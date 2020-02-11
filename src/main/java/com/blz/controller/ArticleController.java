package com.blz.controller;

import com.blz.domain.Article;
import com.blz.service.ArticleService;
import com.blz.utils.ParserJSONUtil;
import org.apache.ibatis.annotations.ResultMap;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 前台
     * 网站首页 简略信息展示
     * 获取最新的三篇生活类文章
     * @return
     */
    @RequestMapping("/lastLifeArticle")
    public @ResponseBody List<Article> findLastLifeArticle(){
        List<Article> articleList = articleService.findArticleListByTheme("life","a.article_date",1,3);
        return articleList;
    }

    /**
     * 前台
     * 网站首页 简略信息展示
     * 获取阅读量最多的的六篇博客文章
     * @return
     */
    @RequestMapping("/mostViewBlogArticle")
    public @ResponseBody List<Article> findMostViewBlogArticle(){
        List<Article> articleList = articleService.findArticleListByTheme("blog","a.article_views",1,6);
        return articleList;
    }
    /**
     * 前台 处理各个类型的文章列表请求
     * @param key
     * @param value
     * @param pageIndex
     * @param rows
     * @return
     */
    @RequestMapping("/list/{key}/{value}/{pageIndex}/{rows}")
    public @ResponseBody List<Article> findArticleListByTheme(@PathVariable("key") String key,@PathVariable("value") String value,@PathVariable("pageIndex") Integer pageIndex,@PathVariable("rows") Integer rows){
        List<Article> articleList = null;
        if(key == null || key.equals("all")){
            articleList = articleService.findAll(pageIndex,rows);
        }else if(key.equals("theme")){
            articleList = articleService.findArticleListByTheme(value,"a.article_date",pageIndex,rows);
        }else if(key.equals("sort")){
            articleList = articleService.findArticleByListSort(value,"a.article_date",pageIndex,rows);
        }else if(key.equals("tag")){

        }else if(key.equals("date")){

        }else if (key.equals("search")){
            articleList = articleService.findByTitleKey(value,pageIndex,rows);
        }
        return articleList;
    }


    /**
     * 前台
     * 处理 获取指定ID的文章的所有信息
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
    public @ResponseBody Article findArticleDetail(@PathVariable("id") Integer articleId) throws Exception {
        Article article = articleService.findById(articleId);
        return article;
    }

    /**
     * 获取文章总数
     * @return
     */
    @RequestMapping("/total")
    public @ResponseBody long findArticleTotal(){
        return articleService.findArticleTotal();
    }

    /**
     * 前台
     * 更新文章的点赞数量
     * @param article
     * @return
     */
    @RequestMapping("/updateLikeCount")
    public @ResponseBody Integer updateArticleLikeCount(@RequestBody Article article){
        return articleService.updateArticleLikeCount(article);
    }

    /**
     * 前台
     * 更新文章的阅读量
     * @param article
     * @return
     */
    @RequestMapping("/updateViews")
    public @ResponseBody Integer updateArticleViews(@RequestBody Article article){
        return articleService.updateArticleViews(article);
    }

    /**
     * 后台
     * 处理获取所有文章的请求
     * @param pageSize
     * @param page
     * @param sortOrder
     * @return
     */
    @RequestMapping(value = "/showArticleList.do",method = RequestMethod.GET)
    public @ResponseBody
    Map<String,Object> showArticleList(@RequestParam(name = "pageSize",required = false) Integer pageSize,
                        @RequestParam(name = "page",required = false) Integer page,
                        @RequestParam(name = "sortOrder",required = false) String sortOrder){
        List<Article> articleList = articleService.findAll(1,1000);
        //System.out.println(articleList.size());
        Map<String,Object> map = new HashMap<>();
        map.put("total",articleList.size());
        map.put("rows",articleList);
        return map;
    }

    /**
     * 后台
     * 处理添加文章的请求
     * @param article
     * @return
     */
    @RequestMapping(value = "/add.do",method = RequestMethod.POST)
    public @ResponseBody long addArticle(@RequestBody Article article){
        return articleService.addArticle(article);
    }

    /**
     * 后台
     * 获得文章的markdown 文档
     * @param id
     * @return
     */
    @RequestMapping(value = "/markdown.do/{id}")
    public @ResponseBody Article findArticleMarkdown(@PathVariable("id") Integer id){
        return articleService.findArticleMarkdown(id);
    }

    /**
     * 后台批量删除文章
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchDelete.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> batchDelete(@RequestBody String ids){

        List<String> idList = ParserJSONUtil.parseBatchDeleteJSONIds(ids);

        long res = articleService.batchDelete(idList);
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
