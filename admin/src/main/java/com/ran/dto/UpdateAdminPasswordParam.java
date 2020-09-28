package com.ran.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 描述:
 *
 * @author RanJianSong
 * @create 2020-09-24 14:19
 */
@Data
public class UpdateAdminPasswordParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;
    @NotEmpty
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}