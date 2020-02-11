package com.blz.service;

import com.blz.domain.Article;

import java.util.List;

/**
 * 文章 Service接口
 */
public interface ArticleService {

    public List<Article> findAll(Integer pageIndex,Integer rows);

    public Article findById(Integer articleId);

    public List<Article> findByTitleKey(String key,Integer pageIndex,Integer rows);

    public List<Article> findArticleListByTheme(String themeName,String sortCol,Integer pageIndex,Integer pageSize);

    public List<Article> findArticleByListSort(String themeName,String sortCol,Integer pageIndex,Integer pageSize);

    public long findArticleTotal();

    public Integer updateArticleLikeCount(Article article);

    public Integer updateArticleViews(Article article);

    public long addArticle(Article article);

    public Article findArticleMarkdown(Integer articleId);

    public long batchDelete(List<String> ids);
}
