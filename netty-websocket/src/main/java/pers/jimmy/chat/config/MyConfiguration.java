package pers.jimmy.chat.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pers.jimmy.chat.bean.Function;
import pers.jimmy.chat.bean.MapperBean;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyConfiguration {

    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    /**
     * 读取xml信息并处理为mysql配置信息
     * @param resource
     * @return
     */
    public Connection build(String resource) {
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            return evalDataSource(root);
        } catch (Exception e) {
            throw new RuntimeException("error occured while evaling xml " + resource);
        }
    }

    private Connection evalDataSource(Element node) throws ClassNotFoundException {
        if (!node.getName().equals("database")) {
            throw new RuntimeException("root should be <database>");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        for (Object property : node.elements("property")) {
            Element e = (Element) property;
            String value = getValue(e);
            String name = e.attributeValue("name");
            if (name == null || value == null) {
                throw new RuntimeException("[database]: <property> should contain name and value");
            }
            switch (name) {
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                case "driverClassName":
                    driverClassName = value;
                    break;
                default:
                    throw new RuntimeException("[database]: <property> unknown name");
            }
        }
        Class.forName(driverClassName);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private String getValue(Element node) {
        return node.hasContent() ? node.getText():node.attributeValue("value");
    }

    public MapperBean readMapper(String path) {
        MapperBean mapperBean = new MapperBean();
        try {
            InputStream stream = loader.getResourceAsStream(path);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            mapperBean.setInterfaceName(root.attributeValue("nameSpace").trim());
            List<Function> list = new ArrayList<>();
            for (Iterator iterator = root.elementIterator();iterator.hasNext();) {
                Function func = new Function();
                Element e = (Element) iterator.next();
                func.setSqltype(e.getName().trim());
                func.setFuncname(e.attributeValue("id").trim());
                Object newInstance = null;
                try {
                    newInstance = Class.forName(e.attributeValue("resultType").trim()).newInstance();
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                func.setResultType(newInstance);
                func.setSql(e.getText().trim());
                list.add(func);
            }
            mapperBean.setList(list);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return mapperBean;
    }

    public static void main(String[] args) {
        MyConfiguration myConfiguration = new MyConfiguration();
//        myConfiguration.build("config.xml");
        myConfiguration.readMapper("UserMapper.xml");
    }

}
