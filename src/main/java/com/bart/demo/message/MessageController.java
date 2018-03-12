package com.bart.demo.message;

import com.bart.demo.config.error.ErrorResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api("/api/messages")
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping
    @ApiOperation(value = "Get all messages", response = Page.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response =  Page.class),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "ex: text,desc", required = false, dataType = "string", paramType = "query")
    })
    Page<Message> getMessages(Pageable pageable) {
        Page<Message> messages = messageService.getMessages(pageable);
        return messages;
    }

    @PostMapping
    @ApiOperation(
            value = "Create new message with user",
            response = Message.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response =  Message.class)
    })
    Message postMessageWithUser(@Valid @RequestBody CreateMessageWithUserDTO createMessageWithUserDTO) {
        return messageService.postMessage(createMessageWithUserDTO);
    }
}
