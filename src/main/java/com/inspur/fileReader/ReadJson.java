package com.inspur.fileReader;

import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.net.URL;


/**
 * 读取配置文件.json
 */
public class ReadJson {
    public static void main(String[] args) {
        String jsonFileName = "/datatypemapping.json";
        JSONObject jsonObj1 = getJsonFile1(jsonFileName);

    }

    private static JSONObject getJsonFile1 (String fileName) {
        String content = loadFile(fileName);
        JSONObject jsonObj = JSONObject.fromObject(content);
        JSONObject dbToStandard = jsonObj.getJSONObject("dbToStandard").getJSONObject("mysql");  // 来源库类型"mysql"对应的数据库字段类型映射
        JSONObject standardToDb = jsonObj.getJSONObject("standardToDb").getJSONObject("oracle");  // 目标库类型"oracle"对应的数据库字段类型映射
        System.out.print("dbToStandard:" + dbToStandard);
        System.out.print("\n");
        System.out.print("standardToDb:" + standardToDb);
        return jsonObj;
    }

    private static String loadFile(String fileName) {
        try {

            URL url = ReadJson.class.getResource(fileName);
            String path = url.getPath();
            // ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要的真实路径，
            // 在此，调用了URLDecoder的decode方法进行解码，以便得到原始的中文及空格路径
            path = URLDecoder.decode(path, "utf-8");
            File file = new File(path);
            if (file.exists()) {
                String content = FileUtils.readFileToString(file, "UTF-8");
                System.out.println(fileName + " file  exist!");
                return content;
            } else {
                System.out.println(fileName + " file not exist!");
            }
        } catch (Exception e) {
            System.out.println("ReadJsonFile error due to {}" + e.getMessage());
        } finally {
            return "";
        }

    }

}
