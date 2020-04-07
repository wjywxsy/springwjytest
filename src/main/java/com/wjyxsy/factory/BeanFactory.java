package com.wjyxsy.factory;

import com.wjyxsy.Annotation.MyAutowired;
import com.wjyxsy.Annotation.MyService;
import com.wjyxsy.Annotation.MyTranscation;
import com.wjyxsy.utils.ClasspathPackageScanner;
import com.wjyxsy.utils.TransactionManager;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.*;

public class BeanFactory {

    private static Map<String, Object> map = new HashMap<>();

    public static Object getBean(String name) {
        return map.get(name);
    }

    static {
        InputStream inputStream = BeanFactory.class.getClassLoader().getResourceAsStream("applicationContext.xml");
        SAXReader reader = new SAXReader();
        try {
            // 读取配置的扫描包路径
            Document document = reader.read(inputStream);
            Element rootElement = document.getRootElement();
            List<Element> list = rootElement.selectNodes("//component-scan");

            // 根据配置的包名扫描其中的类名，放到classNameList中
            Set<String> classNameList = new HashSet<>();
            for (int i = 0; i < list.size(); i++) {
                Element element = list.get(i);
                String basePackage = element.attributeValue("base-package"); // accountDao
                ClasspathPackageScanner scanner = new ClasspathPackageScanner(basePackage);
                List<String> nameList = scanner.getFullyQualifiedClassNameList();
                classNameList.addAll(nameList);
            }

            // 初始化MyService注解的类，并把value和实体类绑定到map中
            for (String className : classNameList) {
                Class<?> aClass = Class.forName(className);
                MyService annotation = aClass.getAnnotation(MyService.class);
                if (annotation == null){
                    continue;
                }
                String key = annotation.value();
                if (key.length() == 0) {
                    throw new RuntimeException("请输入MyService 名称");
                }
                Object o = aClass.newInstance();
                map.put(key, o);
            }

            Iterator<String> beanNameSpaceList = map.keySet().iterator();
            // 1、扫描bean中所有含有MyAutowired()注入的类
            // 2、递归注入类是否含有注入类，依次实例化后进行注入
            while (beanNameSpaceList.hasNext()) {
                String beanAlias = beanNameSpaceList.next();
                initBeanAutowired(beanAlias, map);
            }

            // 循环beans，动态代理bean中含有MyTranscation的方法，进行事务增强
            beanNameSpaceList = map.keySet().iterator();
            while (beanNameSpaceList.hasNext()) {
                String beanAlias = beanNameSpaceList.next();
                Object bean = map.get(beanAlias);
                Class<?> aClass = bean.getClass();
                Method[] methods = aClass.getDeclaredMethods();
                for (Method method : methods) {

                    MyTranscation annotation = method.getAnnotation(MyTranscation.class);
                    if (annotation != null) {
                        ProxyFactory proxyFactory = (ProxyFactory) map.get("proxyFactory");
                        Object jdkProxy = proxyFactory.getJDKProxy(bean);
                        map.put(beanAlias, jdkProxy);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归初始化bean的以来注入
     * @param aliasName
     * @param beansMap
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static void initBeanAutowired(String aliasName, Map<String, Object> beansMap) throws IllegalAccessException, InstantiationException {
        Object bean = map.get(aliasName);
        Class<?> aClass = bean.getClass();
        Field[] fields = aClass.getDeclaredFields();
        if (fields.length == 0) {
            return;
        }
        for (Field declaredField : fields) {
            MyAutowired annotation = declaredField.getAnnotation(MyAutowired.class);
            if (annotation == null) {
                continue;
            }
            String key = annotation.value();
            if (key.length() == 0) {
                throw new RuntimeException("请输入MyAutowired 名称");
            }
            Object o = map.get(key);
            if (o == null) {
                throw new RuntimeException("MyService初始化失败");
            }
            Field[] annoFields = o.getClass().getDeclaredFields();
            if (annoFields.length > 0) {
                // 如果注入的bean还有field 递归，知道找到没有MyAutowired的类
                initBeanAutowired(key, beansMap);
            }
            declaredField.setAccessible(true);
            declaredField.set(bean, o);
            map.put(aliasName,bean);
        }
    }
}
