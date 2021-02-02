package com.inspur.fileReader;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import sun.java2d.cmm.Profile;

import java.io.*;
import java.util.*;

/**
 * 读取配置文件.properties
 */
public class ReadProperty {

    public static void main(String[] args) {
        String propertyName1 = "/code.properties";
        String propertyName2 = "code.properties";
        String propertyName3 = "code";
        String propertyName4 = "code.properties";

        Map<String, Object> profileMap1 = getProfileByInputStream(propertyName1);
//        Map<String, Object> profileMap2 = getProfileByPropertiesLoaderUtils(propertyName2);
//        Map<String, String> profileMap3 = getProfileByResourceBundle(propertyName3);
//        Map<String, Object> profileMap4 = getProfileByClassLoader(propertyName4);
    }


    /**
     * 方法一，基于InputStream读取配置文件
     * @param propertyName
     */
    private static Map<String, Object> getProfileByInputStream(String propertyName) {
        Properties properties = new Properties();
        InputStream inputStream = Object.class.getResourceAsStream(propertyName.trim());
        InputStreamReader inputStreamReader = null;
        Map<String, Object> profileMap = new HashMap<String, Object>();

        try{
            inputStreamReader = new InputStreamReader(inputStream, "utf8");  // 根据自己编码方式配置
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }

        try{
            properties.load(inputStreamReader);
            // 遍历取值
            Set<Object> keys = properties.keySet();
            for(Object key : keys) {
                profileMap.put(key.toString(), properties.getProperty(key.toString()));
                System.out.println(properties.getProperty(key.toString()));
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(properties.get("warshipType.1"));
        return profileMap;
    }


    /**
     * 方法二，通过Spring中的PropertiesLoaderUtils工具类进行获取
     * @Title: getProfileByPropertiesLoaderUtils
     * @Description: Spring 提供的 PropertiesLoaderUtils 允许您直接通过基于类路径的文件地址加载属性资源
     * 最大的好处就是：实时加载配置文件，修改后立即生效，不必重启
     * @return Map<String,Object>
     */
    private static Map<String, Object> getProfileByPropertiesLoaderUtils(String propertyName) {
        Properties properties = new Properties();
        Map<String, Object> profileMap = new HashMap<String, Object>();
        try {
            properties = PropertiesLoaderUtils.loadAllProperties(propertyName.trim());
            System.out.println(new String(properties.getProperty("warshipType.2").getBytes("iso-8859-1"), "utf8"));
            // 遍历取值
            Set<Object> keys = properties.keySet();
            for(Object key : keys) {
                profileMap.put(key.toString(), properties.getProperty(key.toString()));
                System.out.println(new String(properties.getProperty(key.toString()).getBytes("iso-8859-1"), "utf8"));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return profileMap;
    }


    /**
     * 方法三，通过 java.util.ResourceBundle 类获取配置文件资源
     * 优点：1 可以以全限定类名的方式加载资源; 2 可以在非web应用里加载资源
     * 缺点：只能加载类下面的资源文件，且只能读取properties文件
     * @Title: getProfileByResourceBundle
     * @Description: 通过ResourceBundle类获取配置文件资源
     * @param propertyName 配置文件名称
     * 调用方式：
     * 1.配置文件放在resource源包下，不用加后缀
     *  PropertiesUtil.getProfileByResourceBundle("message");
     * 2.放在包里面的
     *  PropertiesUtil.getProfileByResourceBundle("com.test.message");
     * @return Map<String,String> 以Map键值对方式返回配置文件内容
     */
    private static Map<String, String> getProfileByResourceBundle(String propertyName) {
        // 获得资源包
        ResourceBundle resourceBundle = ResourceBundle.getBundle(propertyName.trim());
        // 通过资源包拿到所有的key
        Enumeration<String> allKey = resourceBundle.getKeys();
        Map<String, String> profileMap = new HashMap<String, String>();
        // 遍历key 得到 value
        while(allKey.hasMoreElements()) {
            try {
                String key = allKey.nextElement();
                String value = (String) resourceBundle.getString(key);
                //将文件内容存入map
                profileMap.put(key, value);
                System.out.println( new String(value.getBytes("iso-8859-1"), "utf8"));
            }
            catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            }
        }
        return profileMap;
    }


    /**
     * 方法四，采用ClassLoader(类加载器)方式进行读取配置信息
     * @Title: getProfileByClassLoader
     * @Description: 采用ClassLoader(类加载器)方式进行读取配置信息
     * @return Map<String,Object> 以Map键值对方式返回配置文件内容
     * 优点：可以在非Web应用中读取配置资源信息，可以读取任意的资源文件信息
     * 缺点：只能加载类classes下面的资源文件
     * @throws
     */
    private static Map<String, Object> getProfileByClassLoader(String propertyName) {
        // 通过ClassLoader获取到文件输入流对象
        // 配置文件放在resource源包下,直接写文件名即可,需要后缀名"code.properties"
        // 放在包里面的,需要写上包路径,例如：在test包下"com/test/code.properties"), ReadProperty为当前所在类类名
        InputStream in = ReadProperty.class.getClassLoader().getResourceAsStream(propertyName.trim());
        // 获取文件的位置
        String filePath = ReadProperty.class.getClassLoader().getResource(propertyName).getFile();
        System.out.println(filePath);

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        Properties props = new Properties();
        Map<String, Object> profileMap = new HashMap<String, Object>();
        try {
            props.load(br);
            // 遍历取值
            Set<Object> keys = props.keySet();
            for(Object key : keys) {
                profileMap.put(key.toString(),  props.getProperty(key.toString()));
                System.out.println(props.getProperty(key.toString()));
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return profileMap;
    }

}
