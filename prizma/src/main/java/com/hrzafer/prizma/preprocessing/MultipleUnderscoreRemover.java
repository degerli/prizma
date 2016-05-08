package com.hrzafer.prizma.preprocessing;

/**
 *
 */
public class MultipleUnderscoreRemover extends Filter {

    @Override
    protected boolean eval(String token) {
        return token.indexOf("__") > -1;
    }
}
