package com.hrzafer.prizma.preprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Daha hızlı bir tokenizer yapmak istiyoruz ama henüz doğru çalşımıyor
 */
public class LetterDigitSingeQuoteTokenizer implements ITokenizer {

    @Override
    public List<String> tokenize(String str) {
        String[] tokens = str
                .replaceAll("([^\\p{L}\\p{Nd}']|((?<!\\p{L})')|'(?!\\p{L}))", " ")
                .trim().split("\\s+");

        return new ArrayList<>(Arrays.asList(tokens));
    }
}
