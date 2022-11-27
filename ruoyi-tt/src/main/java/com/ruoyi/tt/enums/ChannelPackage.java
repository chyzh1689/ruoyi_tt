package com.ruoyi.tt.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 可用渠道
 * 用于商户管理。。。默认为0
 *
 */
public enum ChannelPackage {
    TT(1),
    FB(2),
    DY(3);
    private int val;
    ChannelPackage (Integer val){
        this.val= val;
    }
    private static final Map<String,Integer> codeMap = new HashMap<>(4);
    static{
        codeMap.put(TT.name(), TT.val());
        codeMap.put(FB.name(), FB.val());
        codeMap.put(DY.name(), DY.val());
    }
    public static Map<String, Integer> codeMap(){
        return codeMap;
    }

    public static String toChannelStr(Integer mechChannel) {
        StringBuilder sb = new StringBuilder("");
        for (String code : codeMap().keySet()) {
            if(hasChannel(mechChannel,code)){
                sb.append(code+",");
            }
        }
        if(sb.length()>0){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    public Integer val(){
        return val;
    }

    public static boolean hasChannel(int channelPackage,String code){
        return (channelPackage>>(codeMap.get(code)-1))%2==1;
    }

    public static int addChannel(int channelPackage,String code){
        return (1<<(codeMap.get(code)-1))|channelPackage;
    }
    public static int remChannel(int channelPackage,String code){
        return ~(1<<(codeMap.get(code)-1))&channelPackage;
    }
}
