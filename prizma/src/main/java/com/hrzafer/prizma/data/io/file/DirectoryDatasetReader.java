package com.hrzafer.prizma.data.io.file;

import com.hrzafer.prizma.data.*;
import com.hrzafer.prizma.data.io.DatasetReader;
import com.hrzafer.prizma.common.StrUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Reads a dataset from a directory
 */
public class DirectoryDatasetReader extends DatasetReader {

    private File datasetDirectory;
    private String datasetName;

    public DirectoryDatasetReader(String path){
        this(path, DEFAULT_PERCENTAGE, DEFAULT_SHUFFLED_OPTION);
    }
    public DirectoryDatasetReader(String path, double percentage){
        this(path, percentage, DEFAULT_SHUFFLED_OPTION);
    }

    public DirectoryDatasetReader(String path, double percentage, boolean shuffled){
        this.datasetDirectory = new File(path);
        if (!datasetDirectory.exists() || !datasetDirectory.isDirectory()){
            throw new IllegalArgumentException("Dataset directory not found");
        }
        this.percentage = percentage;
        this.shuffled = shuffled;
        this.datasetName = StrUtil.getFileName(path);
    }

    @Override
    public Dataset read() {
        List<DocumentGroup> categories = readCategories();
        return new Dataset(this.datasetName, categories);
    }


    private List<DocumentGroup> readCategories() {
        File[] categoryDirs = getSubdirectoriesIgnoreFiles(datasetDirectory);
        List<DocumentGroup> categories = new ArrayList<>();
        for (File categoryDir : categoryDirs) {
            DocumentGroup category = readCategory(categoryDir);
            categories.add(category);
        }
        return categories;
    }

    private DocumentGroup readCategory(File categoryDir) {
        if (hasSubdirectory(categoryDir)) {
            return readCategoryFromSubcategories(categoryDir);
        }

        return readCategoryFromInstances(categoryDir);
    }

    private SubCategoryDocumentGroup readCategoryFromSubcategories(File categoryDir) {
        List<MainCategoryDocumentGroup> subcategories = new ArrayList<>();
        File[] subsetDirectories = getSubdirectoriesIgnoreFiles(categoryDir);
        for (File subsetDirectory : subsetDirectories) {
            MainCategoryDocumentGroup subset = readCategoryFromInstances(subsetDirectory, categoryDir.getName());
            subcategories.add(subset);
        }
        return new SubCategoryDocumentGroup(subcategories, categoryDir.getName());
    }

    public MainCategoryDocumentGroup readCategoryFromInstances(File categoryDir) {
        List<Document> documents = readAllInstances(categoryDir);
        return new MainCategoryDocumentGroup(documents, categoryDir.getName());
    }

    public MainCategoryDocumentGroup readCategoryFromInstances(File categoryDir, String parentDirectoryName) {
        List<Document> documents = readAllInstances(categoryDir, parentDirectoryName);
        return new MainCategoryDocumentGroup(documents, parentDirectoryName);
    }

    private List<Document> readAllInstances(File directory) {
        return readAllInstances(directory, directory.getName());
    }

    private List<Document> readAllInstances(File directory, String parentDirectoryName) {
        List<Document> documents = new ArrayList<>();
        List<File> textFiles = selectTextFiles(getTextFilesIgnoreOthers(directory));
        for (File textFile : textFiles) {
            Document document = new Document(new FileDocumentSource(textFile.getAbsolutePath(), directory.getName()), parentDirectoryName);
            documents.add(document);
        }
        return documents;
    }

    private List<File> selectTextFiles(List<File> files) {
        if (shuffled) {
            Collections.shuffle(files);
        }
        int count = (int) Math.round((files.size() * percentage) / 100);
        return files.subList(0, count);
    }

    private boolean hasSubdirectory(File directory) {
        return getSubdirectoriesIgnoreFiles(directory).length > 0;
    }

    private File[] getSubdirectoriesIgnoreFiles(File directory) {
        return directory.listFiles(getDirectoryFilter());
    }

    private List<File> getTextFilesIgnoreOthers(File directory) {
        File[] files = directory.listFiles(getTextFileFilter());
        return Arrays.asList(files);
    }

    private FileFilter getDirectoryFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
    }

    private FileFilter getTextFileFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".txt");
            }
        };
    }


}
