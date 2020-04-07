package com.wjyxsy.factory;

import com.wjyxsy.Annotation.MyAutowired;
import com.wjyxsy.Annotation.MyService;
import com.wjyxsy.utils.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;

@MyService("proxyFactory")
public class ProxyFactory {

    @MyAutowired("transactionManager")
    private TransactionManager transactionManager;

    public Object getJDKProxy(Object o) {
        return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), (proxy, method, args) -> {
            Object result = null;
            try {
                transactionManager.beginTransaction();
                result = method.invoke(o, args);
                transactionManager.commit();
            } catch (SQLException e) {
                System.out.println("class=" + proxy.getClass().getName() + ", method="+method.getName()+", params="+args + " error");
                e.printStackTrace();
                transactionManager.rollback();
                throw e;
            }

            return result;
        });
    }

    public Object getCGLibProxy(Object obj) {
        return Enhancer.create(obj.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                try {
                    transactionManager.beginTransaction();
                    result = method.invoke(o, objects);
                    transactionManager.commit();
                } catch (SQLException e) {
                    System.out.println("class=" + o.getClass().getName() + ", method="+method.getName()+", params="+objects + " error");
//                    e.printStackTrace();
                    transactionManager.rollback();
                    throw e;
                }

                return result;
            }
        });
    }


}
