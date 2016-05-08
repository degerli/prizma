package com.hrzafer.prizma.data.io.csv;


import com.hrzafer.prizma.common.Constants;
import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.data.io.DatasetReader;
import com.hrzafer.prizma.common.StrUtil;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads the dataset from a comma separated file.
 * File must be separated by commas (,), and fields must be quoted by double quote (")
 * Double quotes inside the fields must be escaped by another double quote (")
 */
public class CsvDatasetReader extends DatasetReader {

    private final String csvPath;

    public CsvDatasetReader(String csvPath) {
        this.csvPath = csvPath;
    }

    /**
     * Assigns category label to each document, creates categories and returns the data
     */
    @Override
    public Dataset read() {
        List<Document> documents = readInstances();
        return documentsToDataset(documents, StrUtil.getFileName(csvPath));
    }

    /**
     * Reads all instances from CSV
     */
    private List<Document> readInstances() {
        List<Document> documents = new ArrayList<>();
        List<String[]> data;
        try {

            CSVReader csvReader = new CSVReader(
                    new InputStreamReader(new FileInputStream(csvPath), "UTF-8"),
                    CSVParser.DEFAULT_SEPARATOR,
                    CSVParser.DEFAULT_QUOTE_CHARACTER);

            data = csvReader.readAll();
            String[] headers = data.get(0);
            for (int i = 1; i < data.size(); i++) {
                Map<String, String> map = new LinkedHashMap<>();
                String[] row = data.get(i);
                for (int j = 0; j < headers.length - 1; j++) {
                    map.put(headers[j], row[j]);
                }
                String categoryName = row[headers.length - 1];
                map.put(Constants.FieldNames.CATEGORY, categoryName);
                documents.add(new Document(map));
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return documents;
    }

}
