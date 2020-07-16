package com.onesuo.app.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Paths;

/**
 * JSON对象与XML相互转换工具类
 */
public class JsonXmlUtils {
    private static final String ENCODING = "UTF-8";

    /**
     * JSON对象转漂亮的xml字符串
     *
     * @param json JSON对象
     * @return 漂亮的xml字符串
     * @throws IOException
     * @throws SAXException
     */
    public static String jsonToPrettyXml(JSONObject json) throws IOException, SAXException {
        Document document = jsonToDocument(json);

        /* 格式化xml */
        OutputFormat format = OutputFormat.createPrettyPrint();

        // 设置缩进为4个空格
        format.setIndent(" ");
        format.setIndentSize(4);

        StringWriter formatXml = new StringWriter();
        XMLWriter writer = new XMLWriter(formatXml, format);
        writer.write(document);

        return formatXml.toString();
    }

    /**
     * JSON对象转xml字符串
     *
     * @param json JSON对象
     * @return xml字符串
     * @throws SAXException
     */
    public static String JsonToXml(JSONObject json) throws SAXException {
        return jsonToDocument(json).asXML();
    }

    /**
     * JSON对象转Document对象
     *
     * @param json JSON对象
     * @return Document对象
     * @throws SAXException
     */
    public static Document jsonToDocument(JSONObject json) throws SAXException {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(ENCODING);

        // root对象只能有一个
        for (String rootKey : json.keySet()) {
            Element root = jsonToElement(json.getJSONObject(rootKey), rootKey);
            document.add(root);
            break;
        }
        return document;
    }

    /**
     * JSON对象转Element对象
     *
     * @param json JSON对象
     * @param nodeName 节点名称
     * @return Element对象
     */
    public static Element jsonToElement(JSONObject json, String nodeName) {
        Element node = DocumentHelper.createElement(nodeName);
        for (String key : json.keySet()) {
            Object child = json.get(key);
            if (child instanceof JSONObject) {
                node.add(jsonToElement(json.getJSONObject(key), key));
            }
            else {
                Element element = DocumentHelper.createElement(key);
                element.setText(json.getString(key));
                node.add(element);
            }
        }
        return node;
    }

    /**
     * XML字符串转JSON对象
     *
     * @param xml xml字符串
     * @return JSON对象
     * @throws DocumentException
     */
    public static JSONObject xmlToJson(String xml) throws DocumentException {
        JSONObject json = new JSONObject();

        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(xml));
        Element root = document.getRootElement();

        json.put(root.getName(), elementToJson(root));

        return json;
    }

    /**
     * Element对象转JSON对象
     *
     * @param element Element对象
     * @return JSON对象
     */
    public static JSONObject elementToJson(Element element) {
        JSONObject json = new JSONObject();
        for (Object child : element.elements()) {
            Element e = (Element) child;
            if (e.elements().isEmpty()) {
                json.put(e.getName(), e.getText());
            }
            else {
                json.put(e.getName(), elementToJson(e));
            }
        }
        return json;
    }

    /**
     * 文件内容转换成字符串
     *
     * @param filePath 文件路径
     * @return 内容字符串
     * @throws IOException
     */
    public static String fileToString(URL filePath) throws IOException {
        return IOUtils.toString(filePath, ENCODING);
    }

    /**
     * 文件内容转换成字符串
     *
     * @param filePath 文件路径
     * @return 内容字符串
     * @throws IOException
     */
    public static String fileToString(String filePath) throws IOException {
        return IOUtils.toString(Paths.get(filePath).toUri(), ENCODING);
    }

    /**
     * 字符串输出到文件
     *
     * @param str 字符串内容
     * @param filePath 文件路径
     * @throws IOException
     */
    public static void stringToFile(String str, String filePath) throws IOException {
        FileUtils.writeStringToFile(Paths.get(filePath).toFile(), str, ENCODING);
    }

    /**
     * 字符串输出到文件
     *
     * @param str 字符串内容
     * @param filePath 文件路径
     * @throws IOException
     */
    public static void stringToFile(String str, URL filePath) throws IOException {
        FileUtils.writeStringToFile(new File(filePath.getPath()), str, ENCODING);
    }

    /**
     * 字符串输出到文件
     *
     * @param str 字符串内容
     * @param file 输出文件
     * @throws IOException
     */
    public static void stringToFile(String str, File file) throws IOException {
        FileUtils.writeStringToFile(file, str, ENCODING);
    }

    public static void main(String[] args) {
        try {
            String filePath = "/Users/polo/Downloads/山东电子证照综合管理平台/sfz.xml";
            //System.out.println(filePath);
            String content = JsonXmlUtils.fileToString(filePath);
            //System.out.println(content);

            JSONObject json = xmlToJson(content);
            //System.out.println(JSON.toJSONString(json, true));
            System.out.println(json.toString());

            String xml = JsonToXml(json);
            System.out.println(xml);

            //System.out.println("----------------------------------------\n\n");
            xml = jsonToPrettyXml(json);
            //System.out.println(xml);

            //stringToFile(xml, "/Users/polo/Downloads/山东电子证照综合管理平台/User.xml");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}