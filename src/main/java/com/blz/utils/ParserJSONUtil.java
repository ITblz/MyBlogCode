package com.blz.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserJSONUtil {

    /**
     * 对前端传来的要进行批处理删除数据的ids解析出来
     * @param ids
     * @return
     */
    public static List<String> parseBatchDeleteJSONIds(String ids){
        JSONObject jsonObject = JSONObject.parseObject(ids);
        String idsStr = jsonObject.getString("ids");
        List<String> idList = null;
        //判断 数据是一个还是多个
        if (idsStr.indexOf(",") == -1){
            idList = new ArrayList<String>();
            idList.add(idsStr);
        }else {
            String[] idss = idsStr.split(",");
            idList = Arrays.asList(idss);
        }
        return idList;
    }
}
