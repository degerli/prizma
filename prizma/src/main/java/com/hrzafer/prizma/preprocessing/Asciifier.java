package com.hrzafer.prizma.preprocessing;

public class Asciifier extends ModifierFilter {
    @Override
    protected String modify(String token) {
        return asciifyTurkish(token);
    }

    /**
     * Replaces non-ascii chars from Turkish alphabet in a String to ascii equivalents.
     */
    public static String asciifyTurkish(String str) {
        str = str.replace("ç", "c");
        str = str.replace("ö", "o");
        str = str.replace("ş", "s");
        str = str.replace("ğ", "g");
        str = str.replace("ü", "u");
        str = str.replace("ı", "i");
        str = str.replace("Ç", "C");
        str = str.replace("Ö", "O");
        str = str.replace("Ş", "S");
        str = str.replace("Ğ", "G");
        str = str.replace("Ü", "U");
        str = str.replace("İ", "I");
        return str;
    }

    @Override
    protected boolean eval(String token) {
        return true;
    }
}
