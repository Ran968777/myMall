package com.ran.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 描述:
 * 用户登录参数
 *
 * @author RanJianSong
 * @create 2020-09-24 11:22
 */
@Data
public class UmsAdminParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @Email
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    @ApiModelProperty(value = "备注")
    private String note;
}