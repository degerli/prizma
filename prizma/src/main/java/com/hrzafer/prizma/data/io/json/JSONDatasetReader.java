package com.hrzafer.prizma.data.io.json;

import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.data.io.DatasetReader;
import com.hrzafer.prizma.common.StrUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by hrzafer on 06.01.2015.
 */
public class JSONDatasetReader extends DatasetReader {
    private String path;

    public JSONDatasetReader(String path) {
        this.path = path;
    }

    @Override
    public Dataset read() {
        JSONParser parser = new JSONParser();

        try {
            Object obj;
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            String json = new String(bytes, "UTF-8");
            obj = parser.parse(json);

            JSONObject jsonObject = (JSONObject) obj;

//            // loop array
            JSONArray msg = (JSONArray) jsonObject.get("documents");
            List<Document> documents = new ArrayList<>();

            for (int i = 0; i < msg.size(); i++) {
                JSONObject o = (JSONObject) msg.get(i);
                Iterator iterator = o.keySet().iterator();
                Map<String, String> map = new HashMap<>();
                while (iterator.hasNext()){
                    String key = (String) iterator.next();
                    map.put(key, (String) o.get(key));
                }

                documents.add(new Document(map));
            }

            return documentsToDataset(documents, StrUtil.getFileName(path));

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
