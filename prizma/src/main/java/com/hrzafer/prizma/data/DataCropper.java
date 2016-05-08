package com.hrzafer.prizma.data;


import com.hrzafer.prizma.common.Config;
import com.hrzafer.prizma.common.Constants;
import com.hrzafer.prizma.common.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 If a field of a document (usually the content) is too long, this class shortens the field by splitting it to n partsCount and
 reads the first k bytes of each part and merges them. Thus the document size is shortened to n*k=maxSampleLength.
 */
public class DataCropper {

    private static final Logger log = LoggerFactory.getLogger(DataCropper.class);

    private static int maxSampleLength = Config.getAsInt(Constants.Properties.MAX_FIELD_LENGTH);
    private static int partsCount = Config.getAsInt(Constants.Properties.SAMPLING_PARTS_COUNT);

    private static int croppedCount = 0;

    public static Map<String, String> crop(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (isTooLong(entry.getValue())) {
                data.put(entry.getKey(), crop(entry.getValue()));
                croppedCount++;
            }
        }
        log.debug("{} document fields are cropped.", croppedCount);
        return data;
    }

    private static String crop(String str) {
        return StrUtil.getSample(str, maxSampleLength, partsCount);
    }

    private static boolean isTooLong(String str) {
        return str.length() > maxSampleLength;
    }
}
