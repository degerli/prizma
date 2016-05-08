package com.hrzafer.prizma.data;

import java.util.List;

/**
 * Represents a group of documents ( or instances) labeled with the same category ( or class).
 */
public abstract class DocumentGroup {

    private final String category;
    private double trainPercentage;

    public DocumentGroup(String category) {
        this.category = category;
        this.trainPercentage = 100.0;
    }

    public String getCategory() {
        return category;
    }

    public void setTrainPercentage(double trainPercentage) {
        this.trainPercentage = trainPercentage;
    }

    public double getTrainPercentage() {
        return trainPercentage;
    }

    @Override
    public String toString() {
        return "DocumentGroup{" +
                "category='" + category + '\'' +
                ", documentCount=" + getDocumentCount() +
                ", trainPercentage=" + trainPercentage +
                '}';
    }

    /**
     * Shuffles the instances in the category
     */
    abstract public void shuffle();

    abstract public List<Document> getTrainInstances();

    abstract public List<Document> getTestInstances();

    abstract public List<Document> getAllInstances();

    abstract public int getDocumentCount();

    public int getTestInstanceCount() {
        return getDocumentCount() - getTrainInstanceCount();
    }

    public int getTrainInstanceCount() {
        int trainInstanceCount = (int) Math.round((getDocumentCount() * getTrainPercentage()) / 100);
        return trainInstanceCount;
    }
}