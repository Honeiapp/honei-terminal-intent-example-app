package app.honei.honei_terminal_intent_test_app.utils;

import java.util.Locale;

public class MathUtils {
    
    public static double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    
    public static String format2(double value) {
        return String.format(Locale.US, "%.2f", value);
    }
} 