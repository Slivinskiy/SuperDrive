package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE userId=#{userId}")
    User getUser(Integer userId);

    @Insert("INSERT INTO USERS (username, salt, password, firstName, lastName)" +
            "VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUserByUsername(String username);

    @Update("UPDATE User set  salt=#{salt}, password=#{password}, firstName=#{firstName}, lastName=#{lastName} WHERE username=#{username} ")
    User update(String username);

    @Delete("DELETE FROM USERS Where username=#{username}")
    void delete(String username);
}
