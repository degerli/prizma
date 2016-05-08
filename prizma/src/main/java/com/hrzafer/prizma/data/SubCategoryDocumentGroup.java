package com.hrzafer.prizma.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hrzafer
 */
public class SubCategoryDocumentGroup extends DocumentGroup {

    private final List<MainCategoryDocumentGroup> subcategories;

    public SubCategoryDocumentGroup(List<MainCategoryDocumentGroup> subcategories, String name) {
        super(name);
        this.subcategories = subcategories;
    }

    /**
     * The default train percentage is %100.
     * So if train percentage is not set by setTrainPercentage(double trainPercentage)
     * This method returns all the instances.
     */
    @Override
    public List<Document> getTrainInstances() {
        List<Document> documents = new ArrayList<>();
        for (MainCategoryDocumentGroup subcategory : subcategories) {
            subcategory.setTrainPercentage(getTrainPercentage());
            documents.addAll(subcategory.getTrainInstances());
        }
        return documents;
    }

    /**
     * The default test percentage is %0.
     * So if train percentage is not set by setTrainPercentage(double trainPercentage)
     * This method returns an empty List
     */
    @Override
    public List<Document> getTestInstances() {
        List<Document> testSamples = new ArrayList<>();
        for (MainCategoryDocumentGroup subset : subcategories) {
            testSamples.addAll(subset.getTestInstances());
        }
        return testSamples;
    }

    @Override
    public List<Document> getAllInstances() {
        setTrainPercentage(100.0);
        return getTrainInstances();
    }

    @Override
    public void shuffle() {
        for (MainCategoryDocumentGroup subset : subcategories) {
            subset.shuffle();
        }
    }

    @Override
    public int getDocumentCount() {
        int count = 0;
        for (MainCategoryDocumentGroup subset : subcategories) {
            count += subset.getDocumentCount();
        }
        return count;
    }
}
