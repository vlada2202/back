package com.app.ordering_chat;

import com.app.ordering_chat.converter.OrderingChatToOrderingChatDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.app.util.Global.MANAGER;
import static com.app.util.Global.USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderings/chats")
public class OrderingChatController {

    private final OrderingChatService service;
    private final OrderingChatToOrderingChatDtoConverter toDtoConverter;

    @Secured({MANAGER, USER})
    @GetMapping("/{orderingId}")
    public Result findAll(@PathVariable String orderingId) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All",
                service.findAll(orderingId).stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({MANAGER, USER})
    @PostMapping
    public Result save(@RequestParam String text, @RequestParam String orderingId) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(service.save(text, orderingId))
        );
    }

}
