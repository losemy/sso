package com.github.losemy.sso.web.vo;

import com.github.losemy.sso.util.convert.Convert;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: sso
 * Created by lose on 2019/8/27 14:15
 * @author lose
 */
@Data
public class ParamVO<T extends Serializable> extends Convert {
    private static final long serialVersionUID = 1L;

    // 通用字段设计 方便调用传输
    /**
     * 参数
     */
    private T param;

}
