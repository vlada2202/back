package com.app.ordering_chat;

import com.app.app_user.UserService;
import com.app.ordering.OrderingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderingChatService {

    private final OrderingChatRepository repository;
    private final OrderingService orderingService;
    private final UserService userService;

    public List<OrderingChat> findAll(String orderingId) {
        return orderingService.find(orderingId).getChats();
    }

    public OrderingChat save(String text, String orderingId) {
        return repository.save(new OrderingChat(text, orderingService.find(orderingId), userService.getCurrentUser()));
    }

}
