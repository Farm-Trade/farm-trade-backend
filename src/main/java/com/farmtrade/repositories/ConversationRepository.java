package com.farmtrade.repositories;

import com.farmtrade.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query(value = "select * from conversations inner join messages m on conversations.id = m.conversation_id where m.from_user_id=:userId", nativeQuery = true)
    List<Conversation> findAllByUserId(@Param("userId") Long userId);
}
