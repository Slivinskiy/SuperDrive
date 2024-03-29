package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId} ")
    List<Credential> getAllCredentials( Integer userId);

    @Insert("INSERT INTO CREDENTIALS ( url, username, key, password, userId)"+
            "VALUES(  #{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")

    int addCredential(Credential credential);

   @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key = #{key}, password=#{password} WHERE credentialid=#{credentialId} ")
    int updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS Where credentialId=#{credentialId}  ")

    int deleteCredential(Integer credentialId);

}
