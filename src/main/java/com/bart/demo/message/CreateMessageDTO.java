package com.bart.demo.message;

import com.bart.demo.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageDTO {
    @ApiModelProperty(value = "Message, max 140 characters", required = true)
    @NotNull
    @Size(max = Message.MESSAGE_MAX_SIZE)
    private String text;
}
