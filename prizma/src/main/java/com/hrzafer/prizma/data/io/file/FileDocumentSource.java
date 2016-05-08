package com.hrzafer.prizma.data.io.file;

import com.hrzafer.prizma.common.Config;
import com.hrzafer.prizma.common.Constants;
import com.hrzafer.prizma.data.DocumentSource;
import com.hrzafer.prizma.common.FileUtil;
import com.hrzafer.prizma.common.StrUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * To create a Document from a file given its path
 */
public class FileDocumentSource extends DocumentSource {
    private final String encoding = Config.get(Constants.Properties.ENCODING);
    private String path;
    private String subCategory;

    public FileDocumentSource(String path, String subCategory) {
        this.path = path;
        this.subCategory = subCategory;
    }

    @Override
    protected Map<String, String> readData() {
        String content = FileUtil.read(path, encoding);

        Map<String, String> data = new LinkedHashMap<>();

        data.put(Constants.FieldNames.ID, StrUtil.getFileName(path));

        data.put(Constants.FieldNames.CONTENT, content);

        data.put(Constants.FieldNames.SUBCATEGORY, subCategory);

        return data;
    }




}
