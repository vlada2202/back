package com.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderingStatus {
    WAITING("Ожидание"),

    APPROVED("Одобрено"),
    REJECTED("Отклонено"),

    DONE("Выполнено"),
    ;

    private final String name;
}

