package com.blz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文章对应的实体类
 */

@JsonIgnoreProperties(value = {"handler"})
public class Article implements Serializable {
    private Integer  id;//文章id
    private String title;//文章标题
    private String digest;//文章摘要
    private String content;//文章内容
    private Integer views;//文章浏览量
    private Integer comment_count;//文章评论量
    private Date date;//文章发表日期
    private Integer like_count;//文章点赞人数
    private String author;
    private Integer theme_id;
    private Integer sort_id;
    private String markdown;

    private List<Comment> commentList;//文章的评论

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getLike_count() {
        return like_count;
    }

    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(Integer theme_id) {
        this.theme_id = theme_id;
    }

    public Integer getSort_id() {
        return sort_id;
    }

    public void setSort_id(Integer sort_id) {
        this.sort_id = sort_id;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", digest='" + digest + '\'' +
                ", content='" + content + '\'' +
                ", views=" + views +
                ", comment_count=" + comment_count +
                ", date=" + date +
                ", like_count=" + like_count +
                ", author='" + author + '\'' +
                ", theme_id=" + theme_id +
                ", sort_id=" + sort_id +
                ", commentList=" + commentList +
                '}';
    }
}
