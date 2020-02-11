package com.blz.service.impl;

import com.blz.dao.ArticleDao;
import com.blz.domain.Article;
import com.blz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<Article> findAll(Integer pageIndex,Integer rows) {
        return articleDao.findAll((pageIndex-1)*rows,rows);
    }


    @Override
    public Article findById(Integer articleId) {
        return articleDao.findById(articleId);
    }

    @Override
    public List<Article> findByTitleKey(String key,Integer pageIndex,Integer rows) {
        return articleDao.findByTitleKey(("%"+key+"%"),(pageIndex-1)*rows,rows);
    }

    /**
     * 根据传来的主题name 获取相应类型的文章列表数据
     * @param themeName
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<Article> findArticleListByTheme(String themeName, String sortCol, Integer pageIndex, Integer pageSize) {
        return articleDao.findArticleListByTheme(themeName,sortCol,(pageIndex-1)*pageSize,pageSize);
    }

    @Override
    public List<Article> findArticleByListSort(String themeName, String sortCol, Integer pageIndex, Integer pageSize) {
        return articleDao.findArticleListBySort(themeName,sortCol,(pageIndex-1)*pageSize,pageSize);
    }

    @Override
    public long findArticleTotal() {
        return articleDao.findArticleTotal();
    }

    @Override
    public Integer updateArticleLikeCount(Article article) {
        return articleDao.updateLikeCount(article);
    }

    @Override
    public Integer updateArticleViews(Article article) {
        return articleDao.updateViews(article);
    }

    /**
     * 添加文章
     * @param article
     * @return 返回插入数据后文章的id
     */
    @Override
    public long addArticle(Article article) {
        long res = articleDao.insert(article);
        if (res == 1){
            return article.getId();
        }else {
            return -1;
        }
    }

    @Override
    public Article findArticleMarkdown(Integer articleId) {
        return articleDao.findArticleMarkdown(articleId);
    }

    @Override
    public long batchDelete(List<String> ids) {
        return articleDao.batchDelete(ids);
    }

}
