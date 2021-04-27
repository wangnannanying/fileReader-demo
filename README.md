# Java读取配置文件

## 读取配置文件.properties

### 参考博客

https://blog.csdn.net/weixin_39701735/article/details/110655025

https://blog.csdn.net/qq_37725650/article/details/79744847

https://blog.csdn.net/liuyunyihao/article/details/81229009

https://www.jb51.net/article/136564.htm

### 实现方式

参考实现类ReadProperty。

**方法一，基于InputStream读取配置文件。**这种方法通过类的路径来定位properties文件资源的路径，然后通过FileInputStream读取流，最后通过java.util.Properties类的load()方法来加载数据。

方法二，通过Spring中的PropertiesLoaderUtils工具类进行获取。

方法三，通过 java.util.ResourceBundle 类获取配置文件资源。

方法四，采用ClassLoader(类加载器)方式进行读取配置信息。

## 读取配置文件.json

参考实现类ReadJson。

## 读取配置文件.xml

### 参考博客

https://www.cnblogs.com/lanxuezaipiao/archive/2013/05/17/3082949.html

### 实现方式

参考实现类ReadXml。

示例中，使用Dom4j 解析XML文档。