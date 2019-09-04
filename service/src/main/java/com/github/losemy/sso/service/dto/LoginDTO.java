package com.github.losemy.sso.service.dto;

import com.github.losemy.sso.util.convert.Convert;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * Description: sso
 *
 * @author lose on 2019/9/4 15:03
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class LoginDTO extends Convert {
    private static final long serialVersionUID = 1L;

    /**
     * 只支持昵称登录
     */
    @NotEmpty(message = "昵称为空")
    private String name;
    @NotEmpty(message="密码不能为空")
    private String password;
    @NotEmpty(message = "回调地址不能为空")
    private String backUrl;

}
