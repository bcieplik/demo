package com.bart.demo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSubscriptionDTO {
    @ApiModelProperty(value = "Id of a user you want to subscribe to", required = true)
    @NotNull
    private Long userId;
}
