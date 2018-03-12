package com.bart.demo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class CreateUserDTO {
    @ApiModelProperty(value = "User name of a new user", required = true)
    @NotNull
    @Size(max = User.USERNAME_MAX_SIZE)
    private String username;
}
