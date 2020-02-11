package com.blz.dao;

import com.blz.domain.Tag;
import org.apache.ibatis.annotations.*;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDao{

    @Select("select * from tags")
    @Results(id="tagMap",value = {
            @Result(id = true,property = "id",column = "tag_id"),
            @Result(property = "name",column = "tag_name")})
    public List<Tag> findAll();

    @Select("select * from tags where tag_id = #{id}")
    @ResultMap("tagMap")
    public Tag findById(Integer tagId);

    @Select("select * from tags where tag_name = #{name}")
    @ResultMap("tagMap")
    public Tag findByName(String tagName);

    @Insert("insert into tags(tag_name) values(#{name})")
    @Options(useGeneratedKeys=true,keyColumn="tag_id",keyProperty = "id")
    public long insert(Tag tag);

    @Update("update tags set tag_name = #{name} where tag_id = #{id} ")
    public long update(Tag tag);

    @Delete("<script>" +
            " DELETE FROM tags WHERE tag_id in\n" +
            "        <foreach collection=\"ids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">\n" +
            "            #{id}\n" +
            "        </foreach>" +
            "</script>")
    public long batchDelete(@Param("ids") List<String> ids);
}
