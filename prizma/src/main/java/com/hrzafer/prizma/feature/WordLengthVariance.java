package com.hrzafer.prizma.feature;

import com.google.common.primitives.Ints;
import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.feature.value.FeatureValueFactory;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class WordLengthVariance extends Feature {

    public WordLengthVariance(String type, String field, String name, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, field, name, description, parameters, analyzer);
    }

    @Override
    public List<FeatureValue> extract(List<String> tokens) {
        int wordCount = 0;
        int totalWordLen = 0;
        List<Integer> lenghts = new ArrayList();

        for (String token : tokens) {
            int tokenLength = token.length();
            totalWordLen += tokenLength;
            wordCount++;
            lenghts.add(new Integer(tokenLength));
        }

        if (wordCount < 1) {
            return unitList(FeatureValueFactory.create(Double.NaN));
            //return Double.toString(Double.NaN);
        }

        double mean = (double) totalWordLen / wordCount;
        int[] values = Ints.toArray(lenghts);
        double variance = getVariance(values, mean);
        return unitList(FeatureValueFactory.create(variance));
    }

    public static double getVariance(final int[] values, final double mean) {

        double variance = Double.NaN;

        if (values.length == 1) {
            variance = 0.0;
        } else if (values.length > 1) {
            double accumulatorFirst = 0.0;
            double accumulatorSecond = 0.0;
            for (int i = 0; i < values.length; i++) {
                accumulatorFirst += Math.pow((values[i] - mean), 2.0);
                accumulatorSecond += (values[i] - mean);
            }
            variance = (accumulatorFirst - (Math.pow(accumulatorSecond, 2) / ((double) values.length)))
                    / (double) (values.length - 1);
        } else {
            throw new IllegalArgumentException("Length of values[] must be greater than 0");
        }
        return variance;
    }
}
