package cn.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码工具
 */
public class CodeUtil {
    /**
     * 测试验证码是否正确
     *
     * @param request
     * @return
     */
    public static boolean checkVerifyCoide(HttpServletRequest request) {
        //获取期待的验证码
        String verfiyCodeExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

        //获取用户实际输入的验证码
        String verifyCodeActual = (String) request.getParameter("verifyCodeActual");

        //判断服务器的验证码与客户端的验证码是否相等
        if (verifyCodeActual == null || !verifyCodeActual.equals(verfiyCodeExpected)) {
            return false;
        } else {
            return true;
        }
    }
}
