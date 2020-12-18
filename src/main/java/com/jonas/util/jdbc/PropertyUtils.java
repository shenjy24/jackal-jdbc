package com.jonas.util.jdbc;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-10-25
 */
public class PropertyUtils {
    public static Field findField(Object object, PropertyDescriptor propertyDescriptor) throws NoSuchFieldException {
        Class<Object> objectClass = ClassUtils.getRealClass(object);
        return findField(objectClass, propertyDescriptor.getName());
    }

    private static Field findField(Class<Object> objectClass, String propertyName) throws NoSuchFieldException {
        try {
            return objectClass.getDeclaredField(propertyName);
        } catch (NoSuchFieldException e) {
            Class<? super Object> superclass = objectClass.getSuperclass();
            if (!superclass.equals(Object.class)) {
                return findField(superclass, propertyName);
            }
            throw e;
        }
    }

    public static Object[] findFieldValue(Object object) throws IllegalAccessException {
        Field[] fields = ClassUtils.getRealClass(object).getDeclaredFields();
        Object[] values = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            values[i] = field.get(object);
        }
        return values;
    }
}
