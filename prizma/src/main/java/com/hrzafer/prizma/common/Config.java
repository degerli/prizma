package com.hrzafer.prizma.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Global static configuration values (Properties) for Prizma
 */
public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);
    private static Properties properties;

    private Config() { }

    static {
        properties = new Properties();
        load();
    }

    private static void load() {

        InputStream is = Config.class.getClassLoader().getResourceAsStream(Constants.ResourcesPaths.PRIZMA_PROPERTIES);

        if (is == null) {
            log.warn("{} not found in the class path. Default properties will be used.", Constants.ResourcesPaths.PRIZMA_PROPERTIES);
            is = Config.class.getClassLoader().getResourceAsStream(Constants.ResourcesPaths.PRIZMA_DEFAULT_PROPERTIES);
        }
        try {
            properties.load(is);
        } catch (IOException e) {
            log.error("properties can not be loaded");
            throw new RuntimeException(e);
        }
    }

    public static void set(String key, String value) {
        if(properties.containsKey(key)){
            properties.setProperty(key, value);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static int getAsInt(String key) {
        return Integer.parseInt(get(key));
    }

}
