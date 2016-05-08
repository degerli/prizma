package com.hrzafer.prizma.data.io.xml;

import com.hrzafer.prizma.data.Dataset;
import com.hrzafer.prizma.data.Document;
import com.hrzafer.prizma.data.io.DatasetWriter;

/**
 *
 */
public class XmlDatasetWriter extends DatasetWriter {
    @Override
    public void write(Dataset dataset, String path) {

        throw new UnsupportedOperationException("Not ready yet");

//        StringBuilder sb = new StringBuilder("<add>").append(System.lineSeparator());
//        for (Document d : dataset.getAllDocuments()) {
//            try {
//                addDocument(d, sb);
//            }
//            catch (StringIndexOutOfBoundsException e){
//                System.out.println(d.getFieldData("id"));
//            }
//
//        }
//        FileUtil.write(path, sb.append("</add>").toString());
    }

    private void addDocument(Document d, StringBuilder sb) {
        sb.append("<doc>").append(System.lineSeparator())
                .append("<field name=\"id\">")
                .append(d.getFieldData("id").replace('\\','-'))
                .append("</field>").append(System.lineSeparator())
                .append("<field name=\"title\">")
                .append(d.getFieldData("title").replaceAll("&", "&amp;").trim())
                .append("</field>").append(System.lineSeparator())
                .append("<field name=\"content\">")
                .append(d.getFieldData("content").replaceAll("&", "&amp;").trim())
                .append("</field>").append(System.lineSeparator())
                .append("<field name=\"category\">")
                .append(d.getFieldData("category"))
                .append("</field>").append(System.lineSeparator())
                .append("</doc>").append(System.lineSeparator());
    }
}
