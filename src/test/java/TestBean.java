import com.wjyxsy.Annotation.MyService;
import com.wjyxsy.dao.AccountDao;
import com.wjyxsy.factory.BeanFactory;
import com.wjyxsy.factory.CreateFactoryBean;
import com.wjyxsy.pojo.Account;
import com.wjyxsy.service.TransferService;
import com.wjyxsy.test.SonTestObj;
import org.junit.Test;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.annotation.Annotation;
import java.sql.SQLException;

public class TestBean {

    @Test
    public void test() {

//        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        CreateFactoryBean createFactoryBean = (CreateFactoryBean) classPathXmlApplicationContext.getBean("createFactoryBean");
//        System.out.println(createFactoryBean.getName());
//
//        classPathXmlApplicationContext.close();

    }

    @Test
    public void parentTest () throws NoSuchMethodException, ClassNotFoundException {
//        OriTestObj testObj = new ParentTestObj();
//        System.out.println(testObj.getClass().getName());
//
//        testObj.test();

        SonTestObj sonTestObj = new SonTestObj();
        Class<?> aClass = sonTestObj.getClass();
        MyService annotation1 = aClass.getAnnotation(MyService.class);
        System.out.println(annotation1.value());

        Annotation[] annotations = aClass.getAnnotations();
        for (Annotation annotation : annotations) {
            String name = annotation.annotationType().getName();
            System.out.println(annotation);
            System.out.println(name);
        }

//        sonTestObj.test();
    }

    @Test
    public void annoTest() {

        BeanFactory beanFactory = new BeanFactory();
//        AccountDao accountDao = (AccountDao) beanFactory.getBean("accountDao");
        try {
            TransferService transferService = (TransferService) beanFactory.getBean("transferService");
            transferService.transfer("6029621011001", "6029621011000", 1000);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
