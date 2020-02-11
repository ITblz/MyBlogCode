package com.blz.dao;

import com.blz.domain.Article;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao {

    @Select("select * from articles ORDER BY article_date DESC limit #{offset},#{rows}")
    @Results(id = "articleListMap",value = {
            @Result(id = true,property = "id",column = "article_id"),
            @Result(property = "title",column = "article_title"),
            @Result(property = "digest",column = "article_digest"),
            @Result(property = "content",column = "article_content"),
            @Result(property = "date",column = "article_date")
    })
    public List<Article> findAll(@Param("offset") Integer offset,@Param("rows")Integer rows);

    @Select("select * from articles where article_id = #{id}")
    @Results(id = "articleMap",value = {
            @Result(id = true,property = "id",column = "article_id"),
            @Result(property = "title",column = "article_title"),
            @Result(property = "digest",column = "article_digest"),
            @Result(property = "content",column = "article_content"),
            @Result(property = "views",column = "article_views"),
            @Result(property = "comment_count",column = "article_comment_count"),
            @Result(property = "date",column = "article_date"),
            @Result(property = "author",column = "article_author"),
            @Result(property = "like_count",column = "article_like_count"),
            @Result(property = "commentList",column = "article_id",
                    many = @Many(select = "com.blz.dao.CommentDao.findAll",fetchType = FetchType.LAZY)),
            @Result(property = "theme_id",column = "theme_id"),
            @Result(property = "sort_id",column = "sort_id"),
    })
    public Article findById(Integer articleId);

    @Select("select count(*) from articles")
    public long findArticleTotal();

    @Select("select * from articles where article_title like #{key} ORDER BY article_date DESC limit #{offset},#{rows}")
    @ResultMap("articleListMap")
    public List<Article> findByTitleKey(@Param("key") String key,@Param("offset") Integer pageIndex,@Param("rows") Integer rows);

    @Select("SELECT a.* from articles a " +
            "RIGHT OUTER JOIN themes t on t.theme_name = #{themeName} " +
            "WHERE t.theme_id = a.theme_id " +
            "ORDER BY #{sortCol} DESC " +
            "LIMIT #{offset},#{rows}")
    @ResultMap("articleListMap")
    public List<Article> findArticleListByTheme(@Param("themeName") String themeName, @Param("sortCol") String sortCol, @Param("offset") Integer offset, @Param("rows") Integer rows);

    @Select("SELECT a.* from articles a " +
            "RIGHT OUTER JOIN sorts s on s.sort_name = #{sortName} " +
            "WHERE s.sort_id = a.sort_id " +
            "ORDER BY #{sortCol} DESC" +
            "LIMIT #{offset},#{rows}")
    @ResultMap("articleListMap")
    public List<Article> findArticleListBySort(@Param("sortName") String sortName, @Param("sortCol") String sortCol, @Param("offset") Integer offset, @Param("rows") Integer rows);

    @Select("SELECT a.* from articles a " +
            "left outer JOIN set_article_tag sat on  a.article_id = sat.article_id " +
            "left outer JOIN tags t on sat.tag_id = t.tag_id " +
            "WHERE t.tag_name = #{tagName} "+
            "ORDER BY BY #{sortCol} DESC " +
            "LIMIT #{offset},#{rows}")
    @ResultMap("articleListMap")
    public List<Article> findArticleListByTag(@Param("tagName") String sortName, @Param("sortCol") String sortCol, @Param("offset") Integer offset, @Param("rows") Integer rows);

    @Insert("insert into " +
            "articles(article_title,article_author,article_digest,article_content,article_date,sort_id,theme_id,article_markdown) " +
            "values(#{title},#{author},#{digest},#{content},#{date},#{sort_id},#{theme_id},#{markdown})")
    @Options(useGeneratedKeys=true,keyColumn="article_id",keyProperty = "id")//返回插入数据的自增列的值
    public Integer insert(Article article);

    @Select("update articles set article_like_count = #{like_count} where article_id = #{id}")
    public Integer updateLikeCount(Article article);

    @Select("update articles set article_views = #{views} where article_id = #{id}")
    public Integer updateViews(Article article);

    @Select("select * from articles where article_id = #{id}")
    @Results(value = {
            @Result(id = true,property = "id",column = "article_id"),
            @Result(property = "title",column = "article_title"),
            @Result(property = "markdown",column = "article_markdown"),
            @Result(property = "author",column = "article_author"),
            @Result(property = "theme_id",column = "theme_id"),
            @Result(property = "sort_id",column = "sort_id"),
    })
    public Article findArticleMarkdown(Integer id);

    @Delete("<script>" +
            " DELETE FROM articles WHERE article_id in\n" +
            "        <foreach collection=\"ids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">\n" +
            "            #{id}\n" +
            "        </foreach>" +
            "</script>")
    public long batchDelete(@Param("ids") List<String> ids);
}
