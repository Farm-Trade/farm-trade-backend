package com.farmtrade.utils;

import java.math.BigDecimal;

public class BigDecimalUtil {
    public static boolean lessThen(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) < 0;
    }
    public static boolean lessThenOrEqual(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0;
    }
    public static boolean greaterThen(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) > 0;
    }
    public static boolean greaterThenOrEqual(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) >= 0;
    }
    public static boolean equal(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) == 0;
    }
}
