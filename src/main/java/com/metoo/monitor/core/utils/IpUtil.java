package com.metoo.monitor.core.utils;

import org.springframework.stereotype.Component;

@Component
public class IpUtil {


//    public static void main(String[] args) {
//        String mask = "255.255.255.0";
//        System.out.println( toBin(255));
//        System.out.println( toBinInversion(0));
//        String maskInversion = "0.0.0.255";
//        System.out.println(calcByPrefixLengthInversionMack(maskInversion));
//        System.out.println(conversion(32));;
//    }

    public static void main(String[] args) {
        System.out.println(calcPrefixLengthByMack("255.255.255.0"));;
    }
    /**
     * 掩码转换为掩码位
     * @param strip
     * @return
     */
    public static int calcByPrefixLengthInversionMack(String strip) {//输入子网掩码
        StringBuffer sbf;
        String str;
        // String strip = "255.255.255.240; // 子网掩码,结果 28
        int inetmask = 0, count = 0;
        String[] ipList = strip.split("\\.");
        for (int n = 0; n < ipList.length; n++) {
            sbf = toBin(Integer.parseInt(ipList[n]));
            str = sbf.reverse().toString();

            // 统计2进制字符串中1的个数
            count = 0;
            for (int i = 0; i < str.length(); i++) {
                i = str.indexOf('0', i); // 查找 字符'1'出现的位置
                if (i == -1) {
                    break;
                }
                count++; // 统计字符出现次数
            }
            inetmask += count;
        }
        return inetmask;
    }

    /**
     * 掩码位转换为掩掩码
     * @param strip
     * @return
     */
    public static int calcPrefixLengthByMack(String strip) {//输入子网掩码
        StringBuffer sbf;
        String str;
        // String strip = "255.255.255.240; // 子网掩码,结果 28
        int inetmask = 0, count = 0;
        String[] ipList = strip.split("\\.");
        for (int n = 0; n < ipList.length; n++) {
            sbf = toBinInversion(Integer.parseInt(ipList[n]));
            str = sbf.reverse().toString();

            // 统计2进制字符串中1的个数
            count = 0;
            for (int i = 0; i < str.length(); i++) {
                i = str.indexOf('1', i); // 查找 字符'1'出现的位置
                if (i == -1) {
                    break;
                }
                count++; // 统计字符出现次数
            }
            inetmask += count;
        }
        return inetmask;
    }

    public static String conversion(int n){
            int ip = 0xFFFFFFFF << (32 - n);
            String binaryStr = Integer.toBinaryString(ip);
            StringBuffer buffer = new StringBuffer();
            for(int j=0;j<4;j++) {
                int beginIndex = j*8;
                buffer.append(Integer.parseInt(binaryStr.substring(beginIndex, beginIndex+8), 2)).append(".");
            }
//            System.out.println("net mask "+i+" ,submask:"+buffer.substring(0,buffer.length()-1));
        return buffer.substring(0,buffer.length()-1);

    }


    public static StringBuffer toBin(int x) {
        StringBuffer result = new StringBuffer();
        result.append(x % 2);
        x /= 2;
        while (x > 0) {
            result.append(x % 2);
            x /= 2;
        }

        return result;
    }

    public static StringBuffer toBinInversion(int x) {
        x = 255 - x;
        StringBuffer result = new StringBuffer();
        result.append(x % 2);
        x /= 2;
        while (x > 0) {
            result.append(x % 2);
            x /= 2;
        }

        return result;
    }
}
