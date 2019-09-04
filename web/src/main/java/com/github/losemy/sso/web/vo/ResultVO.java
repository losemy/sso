package com.github.losemy.sso.web.vo;

import com.github.losemy.sso.util.convert.Convert;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Description: sso
 * Created by lose on 2019/8/27 14:12
 * @author lose
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResultVO<T extends Serializable> extends Convert {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String respCode;
    /**
     * 返回信息
     */
    private String respMsg;

    /**
     * 数据
     */
    private T data;

    public ResultVO(){
        this.respCode="000000";
        this.respMsg="成功";
    }

}
