package com.blz.dao;

import com.blz.domain.Comment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
//mybatis懒加载 responsebody json序列化时候的异常
//@JsonIgnoreProperties(value = {"javassistProxyFactory", "handler"})

public interface CommentDao {

    @Select("select * from comments where article_id = #{articleId} and comment_checked = 1 ORDER BY comment_date DESC")
    @Results(id = "commentMap",value = {
            @Result(id = true,property = "id",column = "comment_id"),
            @Result(property = "articleId",column = "article_id"),
            @Result(property = "date",column = "comment_date"),
            @Result(property = "content",column = "comment_content"),
            @Result(property = "name",column = "comment_name"),
            @Result(property = "email",column = "comment_email"),
            @Result(property = "like_count",column = "comment_like_count")
    })
    public List<Comment> findAll();


    @Select("select * from comments where comment_id = #{id}")
    @Results(id = "commentMap2",value = {
            @Result(id = true,property = "id",column = "comment_id"),
            @Result(property = "articleId",column = "article_id"),
            @Result(property = "date",column = "comment_date"),
            @Result(property = "content",column = "comment_content"),
            @Result(property = "name",column = "comment_name"),
            @Result(property = "email",column = "comment_email"),
            @Result(property = "like_count",column = "comment_like_count"),
            @Result(property = "checked",column = "comment_checked"),
    })
    public Comment findById(Integer commentId);

    @Select("select * from comments where article_id = #{articleId} and comment_checked = 1 ORDER BY comment_date DESC ")
    @ResultMap("commentMap")
    public List<Comment> findByArticleId(Integer articleId);

    @Insert("insert into comments(article_id,comment_name,comment_email,comment_content,comment_date,comment_checked) " +
            "values(#{articleId},#{name},#{email},#{content},#{date},#{checked})")
    public long insert(Comment comment);

    @Update("update comments set comment_like_count = #{like_count},comment_checked = #{checked} where comment_id = #{id}")
    public Integer update(Comment comment);

    public long delete();

    @Select("select count(*) from comments")
    public long findCommentTotal();


    /*-----后台------*/

    @Select("select * from comments")
    @ResultMap("commentMap2")
    public List<Comment> findAllComments();

    @Update({"<script>" +
            "update comments set comment_checked=1 where comment_id in \n"+
            "<foreach collection=\"ids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">" +
            " #{id}" +
            "</foreach>" +
            "</script>"})
    public long batchUpdateCommentCheckedFiled(@Param("ids") List<String> ids);

    @Delete("<script>" +
            " DELETE FROM comments WHERE comment_id in\n" +
            "        <foreach collection=\"ids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">\n" +
            "            #{id}\n" +
            "        </foreach>" +
            "</script>")
    public long batchDelete(@Param("ids") List<String> ids);
}
