package com.blz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论对应的实体类
 */

@JsonIgnoreProperties(value = {"handler"})
public class Comment implements Serializable {
    private Integer id;
    private Integer articleId;//所属文章的id
    private String name;//评论者的名字
    private Date date;//评论日期
    private String content;//评论内容
    private String email;//评论者的email
    private Integer checked;
    private Integer like_count;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }


    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getLike_count() {
        return like_count;
    }

    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", email='" + email + '\'' +
                ", checked=" + checked +
                ", like_count=" + like_count +
                '}';
    }
}
