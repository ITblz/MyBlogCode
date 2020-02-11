package com.blz.dao;

import com.blz.domain.Sort;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SortDao {

    @Select("select * from sorts")
    @Results(id="sortMap",value = {
            @Result(id = true,property = "id",column = "sort_id"),
            @Result(property = "name",column = "sort_name")})
    public List<Sort> findAll();

    @Select("select * from sorts where sort_name = #{sortName}")
    @Results(value = {
            @Result(id = true,property = "id",column = "sort_id"),
            @Result(id = true,property = "name",column = "sort_name"),
            @Result(property = "articleList",column = "sort_id",many = @Many(select = "com.blz.dao.ArticleDao.findBySortId",fetchType = FetchType.LAZY)),
    })
    public Sort findByName(String sortName);

    @Select("select * from sorts where sort_id = #{sortId}")
    @ResultMap("sortMap")
    public Sort findById(Integer sortId);

    @Insert("insert into sorts(sort_name) values(#{name})")
    @Options(useGeneratedKeys=true,keyColumn="sort_id",keyProperty = "id")
    public long insert(Sort sort);

    @Update("update sorts set sort_name = #{name} where sort_id = #{id}")
    public long update(Sort sort);

    @Delete("<script>" +
            " DELETE FROM sorts WHERE sort_id in\n" +
            "        <foreach collection=\"ids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">\n" +
            "            #{id}\n" +
            "        </foreach>" +
            "</script>")
    public long batchDelete(@Param("ids") List<String> ids);
}
