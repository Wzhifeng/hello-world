package cn.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
    private BigDecimalUtil() {

    }

    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    public static double retainTwo(double target){
        return (double)Math.round(target * 100) / 100;
    }

}

