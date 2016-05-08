package com.hrzafer.prizma.preprocessing;

import com.hrzafer.prizma.feature.StopwordRatio;

/**
 *
 */
public class StopWordRemover extends Filter {
    
    @Override
    protected boolean eval(String token) {
        return StopwordRatio.isStopWord(token);
    }

    
}
