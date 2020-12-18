package com.jonas;

import com.jonas.domain.Account;
import com.jonas.util.ClassUtils;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IllegalAccessException {
        /*Account account = new Account(null, "张三", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        Field[] fields = ClassUtils.getRealClass(account).getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
            field.setAccessible(true);
            System.out.println(field.get(account));
        }*/

        Date d1 = new Date(System.currentTimeMillis());
        Date d2 = new Date(System.currentTimeMillis() + 100);
        System.out.println(d1.compareTo(d2));
    }


}