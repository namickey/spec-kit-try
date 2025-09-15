package com.speckit.message.mapper;

import com.speckit.message.model.Invitation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InvitationMapper {
    @Insert("INSERT INTO invitations(token, contact, expires_at) VALUES(#{token}, #{contact}, #{expiresAt})")
    void insert(Invitation invitation);

    @Select("SELECT token, contact, expires_at as expiresAt FROM invitations WHERE token = #{token}")
    Invitation findByToken(String token);
}
