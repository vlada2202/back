package com.app.ordering_chat;

public record OrderingChatDto(
        Long id,

        String date,

        String role,

        String text
) {
}
