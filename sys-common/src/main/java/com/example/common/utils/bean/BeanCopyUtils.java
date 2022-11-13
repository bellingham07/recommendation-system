package com.example.common.utils.bean;

import cn.hutool.core.bean.BeanUtil;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    public static <V> V copy(Object src, Class<V> clazz) {
        V target = null;
        try {
            target = clazz.newInstance();
            BeanUtil.copyProperties(src, target);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

    public static <T, V> List<V> copyList(List<T> src, Class<V> clazz) {
        return src.stream()
                .map(o -> copy(o, clazz))
                .collect(Collectors.toList());
    }
}
