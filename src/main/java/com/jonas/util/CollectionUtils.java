package com.jonas.util;

import java.util.Collection;

public class CollectionUtils {
    public static boolean isEmpty(Collection collection) {
        return null == collection || 0 == collection.size();
    }

    public static boolean isNotEmpty(Collection collection) {
        return null != collection && 0 < collection.size();
    }
}
