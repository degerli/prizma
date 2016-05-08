package com.hrzafer.prizma.feature.value;


import com.hrzafer.prizma.common.StrUtil;

/**
 *
 */
public class DoubleValue  extends FeatureValue {

    private double value;

    public DoubleValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return StrUtil.formatDouble(value, "#.######");
    }

    @Override
    public FeatureValueType getType() {
        return FeatureValueType.DOUBLE;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
