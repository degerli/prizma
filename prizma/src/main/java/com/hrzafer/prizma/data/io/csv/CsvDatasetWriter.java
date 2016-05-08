package com.hrzafer.prizma.data.io.csv;


import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.data.io.DatasetWriter;
import com.opencsv.CSVParser;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes a dataset to a CSV file
 */
public class CsvDatasetWriter extends DatasetWriter {

    @Override
    public void write(Dataset dataset, String path) {
        CSVWriter writer;

        try {
            writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"),
                    CSVParser.DEFAULT_SEPARATOR,
                    CSVParser.DEFAULT_QUOTE_CHARACTER,
                    CSVParser.DEFAULT_QUOTE_CHARACTER);
            List<Document> documents = dataset.getAllDocuments();
            List<String[]> data = new ArrayList<>();
            List<String> headers = dataset.getFields();
            data.add(headers.toArray(new String[headers.size()]));
            for (Document document : documents) {
                data.add(document.getData());
            }
            writer.writeAll(data);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
