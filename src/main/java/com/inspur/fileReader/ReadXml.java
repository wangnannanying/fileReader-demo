package com.inspur.fileReader;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * Dom4j 解析XML文档
 */
public class ReadXml {
    public static void main(String[] args) {
        String xmlFileName = "/user.xml";
        parserXml(xmlFileName);
    }

    private static void parserXml(String fileName) {
        URL url = ReadJson.class.getResource(fileName);
        String path = url.getPath();
        try{
            // ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要的真实路径，
            // 在此，调用了URLDecoder的decode方法进行解码，以便得到原始的中文及空格路径
            path = URLDecoder.decode(path, "utf-8");
        }catch(UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }

        File inputXml = new File(path);
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(inputXml);
            Element users = document.getRootElement();
            for(Iterator i = users.elementIterator(); i.hasNext(); ) {
                Element user = (Element)i.next();
                for(Iterator j = user.elementIterator(); j.hasNext(); ) {
                    Element node = (Element)j.next();
                    System.out.println(node.getName() + ":" + node.getText());
                }
                System.out.println();
            }

        }catch(DocumentException e) {
            System.out.println(e.getMessage());
        }

    }


}
