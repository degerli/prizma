package com.hrzafer.prizma.common;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * File utils
 */
public class FileUtil {

    private static final int BUFFER = 8192;

    /**
     * Bir String'i bütünüyle dosyaya yazar.<br/> Ör: write("bana_yaz.txt",
     * "beni dosyaya yaz");
     */
    public static void write(String filePath, String content) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
            writer.write(content);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(writer);
        }
    }

    /**
     * Bir dosyayı okuyup bütünüyle string olarak döndürür. Türkçe karakter
     * içeren ANSI biçiminde dosyalar için: read("beni_oku.txt", "ISO-8859-9")
     * şeklinde çağırılması tavsiye edilir Kaynak:
     * http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file/326440#326440
     */
    public static String read(String file, String encoding) {
        // No real need to close the BufferedReader/InputStreamReader
        // as they're only wrapping the stream
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            Reader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName(encoding)));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[BUFFER];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(stream);
        }
    }

    public static String read(InputStream is) {
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[BUFFER];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Dosyayı exception handling ile uğraşmadan kapatmak için.
     */
    private static void close(Closeable closable) {
        if (closable != null) {
            try {
                closable.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static InputStream readAsInputStream(String path) {

        try {
            File file = new File(path);
            if (file.isFile()) {
                return new FileInputStream(file);
            }
        } catch (FileNotFoundException e) {
            return null;
        }

        return null;
    }


}
