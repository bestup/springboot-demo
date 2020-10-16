package com.demo.valid.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtil {

    /**
     * 检验车牌号是否有效
     * @param vehicleNo
     * @return
     */
    public static boolean checkVehicleNo(String vehicleNo) {
        if (null == vehicleNo || "".equals(vehicleNo)) {
            return false;
        }
        String vehicleNoPattern = "^([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领 A-Z]{1}[A-HJ-NP-Z]{1}(([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领 A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9 挂学警港澳]{1})$";
        Pattern pattern = Pattern.compile(vehicleNoPattern);
        return pattern.matcher((String)vehicleNo).find();
    }

    /**
     * 手机号格式校验
     * @param str
     * @return
     */
    public static boolean checkPhone(String str) {
        Pattern pat = Pattern.compile("^[1][34578][0-9]{9}$");
        Matcher mat = pat.matcher(str);
        return mat.find();
    }
}
