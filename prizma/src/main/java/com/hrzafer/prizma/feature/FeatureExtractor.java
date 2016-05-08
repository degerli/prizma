package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.feature.value.FeatureValue;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class FeatureExtractor {
    public static List<FeatureValue> extract(Document document, List<Feature> features) {
        List<FeatureValue> vector = new ArrayList<>();
        for (Feature feature : features) {
            List<FeatureValue> values = feature.extract(document);
            vector.addAll(values);
        }
        return vector;
    }
}
