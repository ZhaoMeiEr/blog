package com.zme.blog.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @authon timber
 * @description 转换工具类
 * @date 2020/2/11 下午11:53
 */
public class TransforUtils {
    /**
     * @Author: timber
     * @Description: 将字符串转换为Long类型的集合
     * @Date: 2020/2/11 下午11:57
     * @param ids 字符串
     * @Return Long类型的集合
     */
    public static List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idArray = ids.split(",");
            for(int i = 0; i < idArray.length; i ++){
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }
}
