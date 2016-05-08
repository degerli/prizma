package com.hrzafer.prizma.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class Resources {

    public static List<String> getLines(String path){
        InputStream is = get(path);
        String str = FileUtil.read(is);
        return StrUtil.toLines(str);
    }

    public static List<String> getLines(String path, boolean encoded){
        String str = read(path);
        if (encoded){
            str = StrUtil.decodeBase64(str);
        }
        return StrUtil.toLines(str);
    }

    public static String read(String path){
        InputStream is = get(path);
        return FileUtil.read(is);
    }

    public static boolean resourceExists(String path){
        if (Resources.class.getClassLoader().getResourceAsStream(path)==null)
            return false;
        return true;
    }

    public static InputStream get(String path) {
        File file = new File(path);
        if (file.isFile()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            InputStream is = Resources.class.getClassLoader().getResourceAsStream(path);
            return is;
        }
        return null;
    }
}
