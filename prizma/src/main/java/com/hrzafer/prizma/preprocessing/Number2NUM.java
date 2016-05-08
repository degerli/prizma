package com.hrzafer.prizma.preprocessing;

public class Number2NUM extends ModifierFilter {

    @Override
    protected boolean eval(String token) {
        //return STR.isInteger(token);
        return isNumber(token);
    }

    private boolean isNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (str.length() > 1) {
                i++;
            } else {
                return false;
            }
        }
        for (; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected String modify(String token) {
        if (isNumber(token)) {
            return "\"NUM\"";
        }
        return token;
    }
}
