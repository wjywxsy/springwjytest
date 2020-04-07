package com.wjyxsy.factory;

//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

//@Component
public class MyProcessor
//        implements BeanPostProcessor
{

//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//
//        if (beanName.equalsIgnoreCase("createFactoryBean")) {
//            System.out.println("createFactoryBean postBeforeInitialization");
//            try {
//                Class<?> aClass = bean.getClass();
//                Field declaredField = aClass.getDeclaredField("name");
//                System.out.println(declaredField.getName());
//                declaredField.setAccessible(true);
//                declaredField.set(bean,"before123");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if (beanName.equalsIgnoreCase("createFactoryBean")) {
//            System.out.println("createFactoryBean postAfterInitialization");
//        }
//        return bean;
//    }
}
