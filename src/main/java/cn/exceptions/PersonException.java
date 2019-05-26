package cn.exceptions;

/**
 * 用户操作异常
 */
public class PersonException extends RuntimeException{
    public PersonException(String message) {
        super(message);
    }
}
