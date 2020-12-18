package com.jonas.util.jdbc;

import java.lang.reflect.Proxy;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-10-25
 */
public class ClassUtils {

    private static final String CGLIB_JAVASSIST_CLASS_SEPARATOR = "$$";
    private static final String BYTE_BUDDY_CLASS_SEPARATOR = "$ByteBuddy$";

    private ClassUtils() {}

    public static <T> Class<T> getRealClass(T object) {
        Class<T> entityClass = (Class<T>) object.getClass();
        return getRealClass(entityClass);
    }

    private static <T> Class<T> getRealClass(Class<T> clazz) {
        if (isProxyClass(clazz)) {
            if (Proxy.isProxyClass(clazz)) {
                Class<?>[] interfaces = clazz.getInterfaces();
                if (interfaces.length != 1) {
                    throw new IllegalArgumentException("Unexpected number of interfaces: " + interfaces.length);
                }
                Class<T> proxiedInterface = (Class<T>) interfaces[0];
                return proxiedInterface;
            }
            Class<T> superclass = (Class<T>) clazz.getSuperclass();
            return getRealClass(superclass);
        }
        return clazz;
    }

    public static boolean isProxyClass(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        if (Proxy.isProxyClass(clazz)) {
            return true;
        }

        return clazz.getName().contains(BYTE_BUDDY_CLASS_SEPARATOR)
                || clazz.getName().contains(CGLIB_JAVASSIST_CLASS_SEPARATOR);
    }
}
