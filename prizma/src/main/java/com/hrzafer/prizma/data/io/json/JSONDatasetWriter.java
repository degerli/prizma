package com.hrzafer.prizma.data.io.json;

import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.data.io.DatasetWriter;
import com.hrzafer.prizma.common.FileUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by hrzafer on 06.01.2015.
 */
public class JSONDatasetWriter extends DatasetWriter {

    @Override
    public void write(Dataset dataset, String path) {

        List<Document> documents = dataset.getAllDocuments();
        List<String> fields = dataset.getFields();
        JSONObject mainObj = new JSONObject();
        JSONArray list = new JSONArray();
        for (Document document : documents) {

            String[] data = document.getData();
            JSONObject obj = new JSONObject();
            int i = 0;
            for (String field : fields) {
                obj.put(field, data[i]);
                i++;
            }
            list.add(obj);
        }

        mainObj.put("documents", list);
        FileUtil.write(path, mainObj.toJSONString());

    }
}
