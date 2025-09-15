package com.speckit.message.mapper;

import com.speckit.message.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users(id, name, contact) VALUES(#{id}, #{name}, #{contact})")
    void insert(User user);

    @Select("SELECT id, name, contact FROM users WHERE id = #{id}")
    User findById(String id);
}
