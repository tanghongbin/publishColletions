package com.android_base.core.common.json;

import java.util.List;

/**
 *
 */
public interface GsonInterface {
    <T> T parseObject(String json, Class<T> tClass);
    <T> String toJSONString(T t);
    <T> List<T> parseJSONArray(String jsonArrat, Class<T> t);
}
