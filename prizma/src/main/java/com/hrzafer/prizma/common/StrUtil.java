package com.hrzafer.prizma.common;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class StrUtil {
    public static String getSample(String source, int maxSampleLength, int partsCount) {
        StringBuilder sb = new StringBuilder();
        int sectionSize = source.length() / partsCount;
        int partSize = maxSampleLength / partsCount;

        //get first partSize characters of each section
        for (int i = 0; i < partsCount; i++) {
            sb.append(source.substring(i * sectionSize, i * sectionSize + partSize));
        }
        return sb.toString();
    }

    public static String addDoubleQuote(String str) {
        return "\"" + str + "\"";
    }


    /**
     * Returns the name of directory or file at the end of the path
     */
    public static String getFileName(String path){
        path = path.replace('\\', '/');
        int index = path.lastIndexOf('/');
        return path.substring(index + 1);
    }



    /**
     * double tipindeki değeri istenilen biçimde String'e dönüştürür.
     * Mesela vigülden sonra en az 2 en fazla 4 basamak gösterilsin istiyorsak
     * pattern = "0.00##" olmalıdır.
     */
    public static String formatDouble(double value, String pattern) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return Double.toString(Double.NaN);
        }
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern(pattern);
        return df.format(value);
    }

    public static String decodeBase64(String str) {
        byte[] decodedBytes = Base64.decode(str);
        return new String(decodedBytes, Charset.forName("UTF-8"));
    }

    public static List<String> toLines(String str) {
        Scanner scanner = new Scanner(str);
        List<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        scanner.close();
        return lines;
    }
}
