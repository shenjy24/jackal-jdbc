package com.jonas.util.jdbc;

import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.Column;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author shenjiayun
 * @since 2019-10-25
 */
public class BeanHandler<T> implements ResultSetHandler<T> {

    // 创建字节码对象
    private Class<T> clazz;

    // 创建有参构造函数，用于传入具体操作对象的类型
    public BeanHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<T> handle(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            while (resultSet.next()) {
                T obj = clazz.newInstance();
                for (PropertyDescriptor descriptor : descriptors) {
                    Field field = PropertyUtils.findField(obj, descriptor);
                    if (field.isAnnotationPresent(Ignore.class)) {
                        continue;
                    }
                    String columnName = descriptor.getName();
                    if (field.isAnnotationPresent(Column.class)) {
                        columnName = field.getAnnotation(Column.class).name();
                    }
                    Object value = resultSet.getObject(columnName);
                    BeanUtils.setProperty(obj, descriptor.getName(), value);
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
