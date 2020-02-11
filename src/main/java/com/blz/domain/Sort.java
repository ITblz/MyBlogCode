package com.blz.domain;

import java.io.Serializable;
import java.util.List;

public class Sort implements Serializable {
    private Integer id;
    private String name;

    private List<Article> articleList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public String toString() {
        return "Sort{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", articleList=" + articleList +
                '}';
    }
}
