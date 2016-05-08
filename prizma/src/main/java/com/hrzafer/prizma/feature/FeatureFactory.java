package com.hrzafer.prizma.feature;

import com.hrzafer.prizma.preprocessing.Analyzer;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 *
 */
public class FeatureFactory {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FeatureFactory.class);

    public static Feature getInstance(String type, String field, String name, String description, Map<String, String> parameters, Analyzer analyzer) {
        String packagePath = "com.hrzafer.prizma.feature";
        try {
            Class<?> c = Class.forName(packagePath + "." + type);
            Constructor<?> cons = c.getConstructor(String.class, String.class, String.class, String.class, Map.class, Analyzer.class);
            return (Feature) cons.newInstance(type, field, name, description, parameters, analyzer);
        } catch (ClassNotFoundException ex) {
            log.error("Feature class {} not found in package {}.", type, packagePath);
            return null;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            log.error("Feature class {} in package {} can not be instantiated.", type, packagePath);
            ex.printStackTrace();
            return null;
        }
    }


}
