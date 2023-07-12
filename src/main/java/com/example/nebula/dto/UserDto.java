package com.example.nebula.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: UserDto
 */

@Data
@ApiModel("用户入参")
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @ApiModelProperty(value = "用户名",required = true,example = "test")
    private String username;

    @ApiModelProperty(value = "密码",required = true,example = "test")
    private String password;
}
