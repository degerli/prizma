package com.hrzafer.prizma.data;

import com.hrzafer.prizma.common.Config;
import com.hrzafer.prizma.common.Constants;

import java.util.Map;

public abstract class DocumentSource {

    private static final boolean cropTooLongData = Config.get(Constants.Properties.ENCODING).equalsIgnoreCase("true");

    public Map<String, String> getData() {
        Map<String, String> data = readData();
        if (cropTooLongData) {
            return DataCropper.crop(data);
        }
        return data;
    }

    protected abstract Map<String, String> readData();
}

