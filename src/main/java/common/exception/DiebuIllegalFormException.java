package common.exception;

import org.springframework.validation.BindingResult;

/**
 * 描 述: 请描述功能
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/5/24 0024
 * 版 本: v1.0
 **/
public class DiebuIllegalFormException extends DiebuException {


    private BindingResult result;
    public DiebuIllegalFormException(BindingResult result){
        super("参数异常");
        this.result = result;
    }

    public BindingResult getBindingResult() {
        return this.result;
    }
}
