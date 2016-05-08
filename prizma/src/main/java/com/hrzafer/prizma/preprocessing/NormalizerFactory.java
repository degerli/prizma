package com.hrzafer.prizma.preprocessing;

import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 */
public class NormalizerFactory {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(NormalizerFactory.class);

    public static ICharFilter create(String className) {
        String packagePath = "com.hrzafer.prizma.preprocessing";
        try {
            Class<?> c = Class.forName(packagePath + "." + className);
            Constructor<?> cons = c.getConstructor();
            return (ICharFilter) cons.newInstance();
        } catch (ClassNotFoundException ex) {
            log.error("Normalizer class {} not found in package {}.", className, packagePath);
            return null;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            log.error("Normalizer class {} in package {} can not be instantiated.", className, packagePath);
            return null;
        }
    }
}
