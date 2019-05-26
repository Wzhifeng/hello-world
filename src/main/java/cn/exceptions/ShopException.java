package cn.exceptions;

/**
 * 商铺操作异常
 */
public class ShopException extends RuntimeException {
    public ShopException(String message) {
        super(message);
    }
}
