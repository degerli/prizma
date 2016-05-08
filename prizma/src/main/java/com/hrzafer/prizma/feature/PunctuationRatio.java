package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.feature.value.DoubleValue;
import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.preprocessing.Analyzer;
import com.hrzafer.prizma.common.StrUtil;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class PunctuationRatio extends SingleTokenFeature {

    protected final char ASTERIKS = '*';
    protected final char COLON = ':';
    protected final char SEMI_COLON = ';';
    protected final char COMMA = ',';
    protected final char DASH = '-';
    protected final char DOUBLE_QUOTE = '"';
    protected final char EXCLAMATION = '!';
    protected final char QUESTION_MARK = '?';
    private PunctuationDataExtractor extractor;
    protected char punctuation;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PunctuationRatio.class);

    public PunctuationRatio(String type, String field, String name, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, field, name, description, parameters, analyzer);

        if(parameters == null || !parameters.containsKey("char")){
            log.error("Missing parameters");
            throw new IllegalArgumentException("Missing parameters");
        }

        punctuation = parameters.get("char").charAt(0);
    }

    @Override
    public List<FeatureValue> extract(String text) {
        double ratio = getPunctuationRatio(text, punctuation);
        FeatureValue value = new DoubleValue(ratio);
        return unitList(value);
    }

    protected String extractRatioAsFormattedString(String source, char ch) {
        double ratio = getPunctuationRatio(source, ch);
        return StrUtil.formatDouble(ratio, "#.###");
    }

    protected double getPunctuationRatio(String source, char ch) {
        extractor = new PunctuationDataExtractor(source, ch);
        int punctuationCount = extractor.getPunctuationCount();
        int totalPunctuationCount = extractor.getTotalPunctuationCount();
        double ratio = ((double) punctuationCount / totalPunctuationCount);
        return ratio;
    }
}
