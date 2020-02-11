package com.blz.dao;

import com.blz.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    @Select("select * from users")
    @Results(id = "userMap",value = {
            @Result(property = "id",column = "user_id"),
            @Result(property = "username",column = "user_name"),
            @Result(property = "nickname",column = "user_nickname"),
            @Result(property = "age",column = "user_age"),
            @Result(property = "gender",column = "user_gender"),
            @Result(property = "email",column = "user_email"),
            @Result(property = "tel",column = "user_telephone_number"),
            @Result(property = "birthday",column = "user_birthday")
    })
    public List<User> findAll();

    @Select("select * from users where user_email = #{email}")
    @ResultMap("userMap")
    public User findByEmail(String email);

    public long insert();

    public long update();

    public long delete();

    @Select("select * from users where user_email = #{email} and user_password = #{pwd}")
    public Integer login(@Param("email") String email,@Param("pwd") String password);

    @Update("update users set user_password=#{newPwd} where user_email = #{email}")
    public Integer changePwd(@Param("email") String email,@Param("newPwd") String newPwd);
}
