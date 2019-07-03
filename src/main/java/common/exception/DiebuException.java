package common.exception;

/**
 * 描 述: 请描述功能
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/5/24 0024
 * 版 本: v1.0
 **/
public class  DiebuException extends  RuntimeException{
    private static final String DEFAULT_CODE = "basic";

    private String errorCode;

    public DiebuException(String message){
        super(message);
        this.errorCode = DEFAULT_CODE;
    }

    public DiebuException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public String getCode() {
        return this.errorCode;
    }
}
