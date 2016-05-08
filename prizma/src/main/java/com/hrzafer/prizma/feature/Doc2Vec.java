package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.feature.value.DoubleValue;
import com.hrzafer.prizma.feature.value.FeatureValue;
import com.hrzafer.prizma.preprocessing.Analyzer;

import java.io.*;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by USER on 19.12.2015.
 * w1 w2 w3
 * <p/>
 * w1 = [d1, d2, ...d50]
 * w2 = [d1, d2, ...d50]
 * w3 = [d1, d2, ...d50]
 * <p/>
 * docvec = [, , ... ]
 */
public class Doc2Vec extends Feature {

    public Map<String, double[]> vectorMap;
    private int vectorSize;
    private final String vectorsFilePath;
    private final NumberFormat format = NumberFormat.getInstance(Locale.US);


    public Doc2Vec(String type, String field, String name, String description, Map<String, String> parameters, Analyzer analyzer) {
        super(type, field, name, description, parameters, analyzer);
        if (!parameters.containsKey("path")) {
            throw new RuntimeException("Path for vectors file must be defined!!!");
        }
        vectorsFilePath = parameters.get("path");
        vectorSize = getSize();
        System.out.println("Word embedding vector size:" + vectorSize);
        System.out.println("Loading word embeddings. This can take a while...");
        System.out.println(Locale.getDefault());
        populateMap();

    }

    @Override
    public List<FeatureValue> extract(List<String> tokens) {
        List<FeatureValue> list = new ArrayList<>();

        double[] values = getAverage(tokens);

        for (int i = 0; i < values.length; i++) {
            list.add(new DoubleValue(values[i]));
        }

        return list;
    }

    private void lineToVec(String line, Map<String, double[]> map) {
        String[] tokens = line.split(" ");
        String key = tokens[0];
        double[] vectors = new double[vectorSize];
        for (int i = 1; i < tokens.length; i++) {

            try{
                vectors[i - 1] = format.parse(tokens[i]).doubleValue();
            }
            catch (Exception e){
                return;
            }

        }
        map.put(key, vectors);
    }

    private void populateMap() {

        String line;
        long counter = 0;
        vectorMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(vectorsFilePath), Charset.forName("ISO-8859-9")))) {
            while ((line = br.readLine()) != null) {
                {
                    lineToVec(line, vectorMap);
                    counter++;
                    if (counter % 10_000 == 0) {
                        System.out.println(counter + " embeddings are read");
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Add single to total
    private void add(double[] total, double[] single) {
        for (int i = 0; i < total.length; i++) {
            total[i] += single[i];
        }
    }

    private void avg(double[] total, int size) {
        for (int i = 0; i < total.length; i++) {
            total[i] /= size;
        }
    }

    // her dokuman icin ortalama vektor oluşturuyor
    public double[] getAverage(List<String> tokens) {
        double[] totalVector = new double[vectorSize];
        List<String[]> stringArrayList = new ArrayList<>();
        for (String token : tokens) {
            if (vectorMap.containsKey(token)) {
                double[] vector = vectorMap.get(token);
                add(totalVector, vector);
            }
        }

        avg(totalVector, tokens.size());

        return totalVector;
    }

    private int getSize() {
        try (BufferedReader br = new BufferedReader(new FileReader(vectorsFilePath))) {
            return br.readLine().split(" ").length - 1;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException: ", e);
        }
    }

    @Override
    public String getDeclarationForArff() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vectorSize; i++) {
            sb.append("@attribute ").
                    append("dim" + i).append(field).append(" ").
                    append("numeric").append("\n");
        }
        return sb.toString();
    }
}
