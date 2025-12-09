package com.app.ordering_chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderingChatRepository extends JpaRepository<OrderingChat, Long> {
}
