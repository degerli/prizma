package com.hrzafer.prizmaclient;

import com.hrzafer.prizma.common.Resources;
import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.data.io.DatasetReader;
import com.hrzafer.prizma.data.io.DatasetWriter;
import com.hrzafer.prizma.data.io.csv.CsvDatasetWriter;
import com.hrzafer.prizma.data.io.csv.CsvDatasetReader;
import com.hrzafer.prizma.data.io.file.DirectoryDatasetReader;
import com.hrzafer.prizma.data.io.json.JSONDatasetReader;
import com.hrzafer.prizma.data.io.json.JSONDatasetWriter;
import com.hrzafer.prizma.experiment.FeaturesReader;
import com.hrzafer.prizma.experiment.XmlFeaturesReader;
import com.hrzafer.prizma.feature.Feature;

import java.util.List;

/**
 * Sample client code using Prizma
 */
public class Main {
    public static void main(String[] args) {

        //ReadDatasetFromDirectory();
        //ReadDatasetFromCsv();
        //DatasetFromDirToCsv();
        //DatasetFromDirToJson();
        //ReadDatasetFromCsv();



        FeaturesReader featuresReader = new XmlFeaturesReader(Resources.get("featureSet.xml"));

        List<Feature> features = featuresReader.read();
        System.out.println(features);
    }



    static void DatasetFromDirToJson(){

        String dir = "C:\\Users\\harun_000\\Dropbox\\prizma\\prizma\\dataset\\sample_dataset_sub";

        DatasetReader datasetReader = new DirectoryDatasetReader(dir);

        Dataset dataset = datasetReader.read();

        DatasetWriter datasetWriter = new JSONDatasetWriter();

        datasetWriter.write(dataset, "deneme.json");
    }

    static void DatasetFromDirToCsv(){
        String dir = "C:\\Users\\harun_000\\Dropbox\\prizma\\prizma\\dataset\\sample_dataset_sub";

        DatasetReader datasetReader = new DirectoryDatasetReader(dir);

        Dataset dataset = datasetReader.read();

        DatasetWriter writer = new CsvDatasetWriter();
        writer.write(dataset, "deneme.csv");
    }

    static void ReadDatasetFromJson(){
        String dir = "deneme.json";
        DatasetReader datasetReader = new JSONDatasetReader(dir);
        Dataset dataset = datasetReader.read();
        System.out.println(dataset);
    }

    static void ReadDatasetFromCsv(){
        String dir = "deneme.csv";
        DatasetReader datasetReader = new CsvDatasetReader(dir);
        Dataset dataset = datasetReader.read();
        System.out.println(dataset);
    }

    static void ReadDatasetFromDirectory(){

        String dir = "C:\\Users\\harun_000\\Dropbox\\prizma\\prizma\\dataset\\sample_dataset_sub";

        DatasetReader datasetReader = new DirectoryDatasetReader(dir);

        Dataset dataset = datasetReader.read();

        System.out.println(dataset);

        List<Document> documents = dataset.getAllDocuments();


        for (Document document : documents) {
            System.out.println(document);
        }


    }


}
