package common.exception;

/**
 * 描 述: 请描述功能
 * 作 者: ZhouLin
 * 日 期: 创建时间: 2019/5/24 0024
 * 版 本: v1.0
 **/
public class DiebuUnauthenticatedException extends  DiebuException{
    public DiebuUnauthenticatedException() {
        super("权限不足", "403");
    }

    public DiebuUnauthenticatedException(String message) {
        super(message, "403");
    }
}
