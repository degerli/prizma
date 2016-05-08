package com.hrzafer.prizma.data.io;

import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.data.DocumentGroup;
import com.hrzafer.prizma.data.MainCategoryDocumentGroup;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public abstract class DatasetReader {

    /** Default percentage value is %100 */
    public static final double DEFAULT_PERCENTAGE = 100.0;
    /** Default Shuffle Option = false */
    public static final boolean DEFAULT_SHUFFLED_OPTION = false;
    /** Default Charset = UTF-8 */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    protected double percentage = DEFAULT_PERCENTAGE;
    protected boolean shuffled = DEFAULT_SHUFFLED_OPTION;
    protected Charset charset = DEFAULT_CHARSET;

    public abstract Dataset read();

    protected Dataset documentsToDataset(List<Document> documents, String name){
        List<Document> instancesOfCategory = new ArrayList<>();
        List<DocumentGroup> categories = new ArrayList<>();
        Collections.sort(documents);
        String categoryName = documents.get(0).getActualCategory();
        for (Document document : documents) {
            if (categoryName.equals(document.getActualCategory())) {
                instancesOfCategory.add(document);
            } else {
                categories.add(new MainCategoryDocumentGroup(instancesOfCategory, categoryName));
                instancesOfCategory = new ArrayList<>();
                instancesOfCategory.add(document);
                categoryName = document.getActualCategory();
            }
        }
        categories.add(new MainCategoryDocumentGroup(instancesOfCategory, categoryName));
        return new Dataset(name, categories);
    }

}

