package cn.tycoding.scst.common.utils;

import cn.tycoding.scst.common.constant.CommonConstants;
import cn.tycoding.scst.common.constant.enums.CommonEnums;
import lombok.*;

import java.io.Serializable;

/**
 * @author tycoding
 * @date 2019-06-02
 */
@Builder
@ToString
@AllArgsConstructor
public class R<T> implements Serializable {

    @Getter
    @Setter
    private int code = CommonConstants.SUCCESS;

    @Getter
    @Setter
    private Object msg = "success";

    @Getter
    @Setter
    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public R(CommonEnums enums) {
        super();
        this.code = enums.getCode();
        this.msg = enums.getMsg();
    }

    public R(Throwable e) {
        super();
        this.code = CommonConstants.ERROR;
        this.msg = e.getMessage();
    }

    public R(String message, Throwable e) {
        super();
        this.msg = message;
        this.code = CommonConstants.ERROR;
    }
}
