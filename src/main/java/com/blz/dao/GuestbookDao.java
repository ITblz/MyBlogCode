package com.blz.dao;

import com.blz.domain.Guestbook;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestbookDao {

    @Select("select * from guestbook ORDER BY guestbook_date DESC LIMIT #{offset},#{rows}")
    @Results(id = "guestbookMap",value = {
            @Result(id = true,column ="guestbook_id",property = "id"),
            @Result(column ="guestbook_name",property = "name"),
            @Result(column ="guestbook_content",property = "content"),
            @Result(column ="guestbook_date",property = "date"),
            @Result(column ="guestbook_checked",property = "checked")
    })
    public List<Guestbook> findGuestbookList(@Param("offset") Integer offset,@Param("rows") Integer rows);

    @Select("select * from guestbook where guestbook_checked = 1 ORDER BY guestbook_date DESC LIMIT #{offset},#{rows}")
    @ResultMap("guestbookMap")
    public List<Guestbook> findGuestbookListByChecked(@Param("offset") Integer offset,@Param("rows") Integer rows);

    @Insert("insert into guestbook(guestbook_name,guestbook_content,guestbook_date) values(#{name},#{content},#{date})")
    public Integer insert(Guestbook guestbook);

    @Delete("<script>" +
            " DELETE FROM guestbook WHERE guestbook_id in\n" +
            "        <foreach collection=\"ids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">\n" +
            "            #{id}\n" +
            "        </foreach>" +
            "</script>")
    public Integer batchDelete(@Param("ids") List<String> ids);

    @Update({"<script>" +
            "update guestbook set guestbook_checked=1 where guestbook_id in \n"+
            "<foreach collection=\"ids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">" +
            " #{id}" +
            "</foreach>" +
            "</script>"})
    public long batchUpdateGuestbookCheckedFiled(@Param("ids") List<String> ids);
}
