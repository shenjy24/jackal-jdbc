package com.jonas;

import com.jonas.domain.Account;
import com.jonas.util.ClassUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IllegalAccessException {
        Account account = new Account(null, "张三", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        Field[] fields = ClassUtils.getRealClass(account).getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
            field.setAccessible(true);
            System.out.println(field.get(account));
        }
    }


}