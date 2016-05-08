package com.hrzafer.prizma.data;

import java.util.ArrayList;
import java.util.List;


public class Dataset {

    private final String name;
    private final List<DocumentGroup> groups;
    private final List<String> fields;

    public Dataset(String name, List<DocumentGroup> groups) {
        this.name = name;
        this.groups = groups;
        this.fields = groups.get(0).getAllInstances().get(0).getFieldNames();
    }

    public String getName() {
        return name;
    }

    public List<String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "name='" + name + '\'' +
                ", groups=" + groups +
                ", fields=" + fields +
                '}';
    }

    public List<Document> getAllDocuments() {
        return getPercentageSplittedInstances(100);
    }

    /**
     * Her sınıftan belirtilen yüzdede eğitim ve test numunesi alır.
     * Böylece verinin ilk bölümü farklı sınıflardan eşit yüzdede eğitim numuneleri bulunur.
     * Kalan kısımda ise yine her sınıftan eşit yüzdelerde test numuneleri bulunur.
     *
     * @param trainPercentage eğitim verisinin yüzdesi
     * @return
     */
    public List<Document> getPercentageSplittedInstances(double trainPercentage) {

        List<Document> documents = new ArrayList<>();
        for (DocumentGroup group : groups) {
            group.setTrainPercentage(trainPercentage);
            documents.addAll(group.getTrainInstances());
        }

        for (DocumentGroup category : groups) {
            documents.addAll(category.getTestInstances());
        }
        return documents;
    }

    /**
     * Return instances of the specified category
     */
    public List<Document> getDocumentsOf(String categoryName) {
        return getDocumentsOf(categoryName, 100.0);
    }

    public List<Document> getDocumentsOf(String categoryName, double percentage) {
        List<Document> samples = new ArrayList<>();
        for (DocumentGroup group : groups) {
            if (group.getCategory().equalsIgnoreCase(categoryName)) {
                group.setTrainPercentage(percentage);
                samples.addAll(group.getTrainInstances());
                return samples;
            }
        }
        return null;
    }

    public List<String> getKlassNames() {
        List<String> names = new ArrayList<>();
        for (DocumentGroup category : groups) {
            String name = category.getCategory();
            names.add(name);
        }
        return names;
    }

    /**
     * Shuffles the instances to make the instances in training and test sets be different
     */
    public void shuffle() {
        for (DocumentGroup category : groups) {
            category.shuffle();
        }
    }

    public Evaluation evaluate() {
        int correct = 0;
        int wrong = 0;
        List<Document> allDocuments = getAllDocuments();
        for (Document document : allDocuments) {
            if (document.getActualCategory().equals(document.getPredictedCategory())) {
                correct++;
            } else {
                wrong++;
            }
        }

        return new Evaluation(correct, wrong);

    }

    public int getInstanceCount() {
        int count = 0;
        for (DocumentGroup category : groups) {
            count += category.getDocumentCount();
        }
        return count;
    }

    public int getTrainInstanceCount() {
        int count = 0;
        for (DocumentGroup category : groups) {
            count += category.getTrainInstanceCount();
        }
        return count;
    }
}

