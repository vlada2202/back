package com.app.ordering_chat.converter;

import com.app.ordering_chat.OrderingChat;
import com.app.ordering_chat.OrderingChatDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderingChatToOrderingChatDtoConverter implements Converter<OrderingChat, OrderingChatDto> {
    @Override
    public OrderingChatDto convert(OrderingChat source) {
        return new OrderingChatDto(
                source.getId(),

                source.getDate(),

                source.getRole(),

                source.getText()
        );
    }
}
