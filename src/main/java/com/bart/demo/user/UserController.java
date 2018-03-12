package com.bart.demo.user;

import com.bart.demo.config.error.ErrorResponse;
import com.bart.demo.message.Message;
import com.bart.demo.message.MessageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Api("/api/users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @GetMapping
    @ApiOperation(value = "Get all users", response = Page.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response =  Page.class),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "ex: username,desc", required = false, dataType = "string", paramType = "query")
    })
    Page<User> getUsers(Pageable pageable) {
        Page<User> users = userService.getUsers(pageable);
        return users;
    }

    @PostMapping("/{userId}/subscriptions")
    @ApiOperation(value = "Subscribe to other user posts", response = User.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = User.class),
            @ApiResponse(code = 404, message = "One or both users not found")
    })
    User subscribeToUser(
            @PathVariable Long userId,
            @Valid @RequestBody AddSubscriptionDTO addSubscriptionDTO,
            Pageable pageable) {
        return userService.addSubscription(userId, addSubscriptionDTO);
    }

    @GetMapping("/{userId}/subscriptions")
    @ApiOperation(value = "Get users subscriptions", response = User[].class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response =  User[].class),
            @ApiResponse(code = 404, message = "User not found")
    })
    Set<User> getSubscribtions(@PathVariable Long userId) {
        return userService.getSubscriptions(userId);
    }

    @GetMapping("/{userId}/wall")
    @ApiOperation(value = "Get user wall", response = Page.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response =  Page.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page", required = false, dataType = "int", paramType = "query"),
    })
    Page<Message> getWall(@PathVariable Long userId, Pageable pageable) {
        return messageService.getWall(userId, pageable);
    }

    @GetMapping("/{userId}/timeline")
    @ApiOperation(value = "Get user timeline", response = Page.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response =  Page.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page", required = false, dataType = "int", paramType = "query"),
    })
    Page<Message> getTimeline(@PathVariable Long userId, Pageable pageable) {
        return messageService.getTimeline(userId, pageable);
    }
}
