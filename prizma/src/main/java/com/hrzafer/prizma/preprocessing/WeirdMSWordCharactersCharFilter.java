package com.hrzafer.prizma.preprocessing;

/**
 *
 */
public class WeirdMSWordCharactersCharFilter implements ICharFilter {
    @Override
    public String normalize(String str) {
        return replaceWeirdMSWordCharacters(str);
    }

    private String replaceWeirdMSWordCharacters(String str) {
        str = str.replace("''", "\"");
        str = str.replace((char) 145, '\'');
        str = str.replace((char) 8216, '\''); // left single quote
        str = str.replace((char) 146, '\'');
        str = str.replace((char) 8217, '\''); // right single quote
        str = str.replace((char) 147, '\"');
        str = str.replace((char) 148, '\"');
        str = str.replace((char) 8220, '\"'); // left double
        str = str.replace((char) 8221, '\"'); // right double
        str = str.replace((char) 8211, '-'); // em dash
        str = str.replace((char) 8212, '-'); // em dash
        str = str.replace((char) 150, '-');// ?
        str = str.replaceAll("…", "...");
        return str;
    }

}
