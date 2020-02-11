package com.blz.dao;

import com.blz.domain.Theme;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeDao {

    @Select("select * from themes")
    @ResultMap("themeMap")
    public List<Theme> findAll();

    @Select("select * from themes where theme_id = #{id}")
    @Results(id = "themeMap",value = {
            @Result(id = true,property = "id",column = "theme_id"),
            @Result(property = "name",column = "theme_name")
    })
    public Theme findById(Integer id);

    @Select("select * from themes where theme_name = #{name}")
    @ResultMap("themeMap")
    public Theme findByName(String name);


    @Insert("insert into themes(theme_name) values(#{name})")
    @Options(useGeneratedKeys=true,keyColumn="theme_id",keyProperty = "id")
    public long insert(Theme theme);

    @Update("update themes set theme_name = #{name} where theme_id = #{id}")
    public long update(Theme theme);

    @Delete("<script>" +
            " DELETE FROM themes WHERE theme_id in\n" +
            "        <foreach collection=\"ids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">\n" +
            "            #{id}\n" +
            "        </foreach>" +
            "</script>")
    public long batchDelete(@Param("ids") List<String> ids);
}
