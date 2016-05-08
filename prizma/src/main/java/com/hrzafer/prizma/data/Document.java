package com.hrzafer.prizma.data;


import com.hrzafer.prizma.common.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Document implements Comparable<Document> {

    private final Map<String, String> data;

    public Document(DocumentSource source, String actualCategory) {
        data = source.getData();
        data.put(Constants.FieldNames.CATEGORY, actualCategory);
    }

    public Document(DocumentSource source) {
        this(source.getData());
    }

    public Document(Map<String, String> data) {
        this.data = data;
        checkCategoryField();
    }

    private void checkCategoryField() {
        if (!data.containsKey(Constants.FieldNames.CATEGORY)) {
            data.put(Constants.FieldNames.CATEGORY, "?");
        }
    }

    public String getActualCategory() {
        return data.get(Constants.FieldNames.CATEGORY);
    }

    public String getPredictedCategory() {
        return data.get(Constants.FieldNames.PREDICTED_CATEGORY);
    }

    public String getSubCategory() {
        return data.get(Constants.FieldNames.SUBCATEGORY);
    }

    public String getFieldData(String fieldName) {
        return data.get(fieldName);
    }

    public List<String> getFieldNames() {
        return new ArrayList<>(data.keySet());
    }

    public void setPredictedCategory(String predictedCategory) {
        data.put(Constants.FieldNames.PREDICTED_CATEGORY, predictedCategory);
    }

    public String[] getData() {
        return data.values().toArray(new String[data.size()]);
    }

    @Override
    public int compareTo(Document other) {
        return this.getActualCategory().compareTo(other.getActualCategory());
    }

    /**
     * Returns if the actual category equals to the predicted category.
     * If either of actual or predicted category is empty, returns false.
     */
    public boolean classifiedCorrectly() {
        if (!getActualCategory().isEmpty() && !getActualCategory().isEmpty()) {
            return getActualCategory().equals(getPredictedCategory());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + data.get(Constants.FieldNames.ID) +
                ", category=" + data.get(Constants.FieldNames.CATEGORY) +
                ", subcategory=" + data.get(Constants.FieldNames.SUBCATEGORY) +
                ", predictedCategory=" + data.get(Constants.FieldNames.PREDICTED_CATEGORY) +
                '}';
    }
}
