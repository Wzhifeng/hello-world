package cn.exceptions;

/**
 * 商铺分类操作异常
 */
public class ShopCategoryException extends RuntimeException {
    public ShopCategoryException(String message) {
        super(message);
    }
}
