package com.hsia.weblog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.servlet.http.HttpSession;

/**
 * @author: hsia
 * @Date: 2018/2/17 下午10:23
 * @Description:
 */
@Data
@ApiModel
public class LoginVo {
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空！")
    String username;
    @NotBlank(message = "密码不能为空！")
    @ApiModelProperty(value = "密码")
    String password;

    HttpSession session;
}
