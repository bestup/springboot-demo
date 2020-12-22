package com.example.util;

import cn.hutool.core.util.StrUtil;

public class StringUtil {

    public static String getUUID() {
        return StrUtil.uuid().replaceAll("-","");
    }
}
