package com.github.losemy.sso.client.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Description: sso
 * 存放对外user信息
 * @author lose on 2019/9/4 9:19
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String email;

}
