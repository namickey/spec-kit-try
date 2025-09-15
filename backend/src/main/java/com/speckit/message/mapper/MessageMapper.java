package com.speckit.message.mapper;

import com.speckit.message.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Insert("INSERT INTO messages(id, sender_id, body, attachment_ref, created_at) VALUES(#{id}, #{senderId}, #{body}, #{attachmentRef}, #{createdAt})")
    void insert(Message message);

    @Insert("INSERT INTO message_recipients(message_id, recipient_id) VALUES(#{messageId}, #{recipientId})")
    void insertRecipient(@Param("messageId") String messageId, @Param("recipientId") String recipientId);

    @Select("SELECT m.id, m.sender_id as senderId, m.body, m.attachment_ref as attachmentRef, m.created_at as createdAt FROM messages m JOIN message_recipients r ON m.id = r.message_id WHERE r.recipient_id = #{recipientId}")
    @Results({
            @Result(column = "senderId", property = "senderId"),
            @Result(column = "attachmentRef", property = "attachmentRef"),
            @Result(column = "createdAt", property = "createdAt")
    })
    List<Message> findByRecipientId(String recipientId);
}
