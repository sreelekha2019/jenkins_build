package war.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: Aaron
 * @Date: 2018/7/10 10:52
 * @Description: SHA256 摘要算法工具类
 */
public class SHA256Util {
    /**
     *
     * 功能描述: 利用java原生的摘要实现SHA256加密
     *
     * @param: str 加密后的报文
     * @return: java.lang.String
     * @auther: Aaron
     * @date: 2018/7/10 10:49
     */
    public static String getSHA256(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try{
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return encodeStr;
    }


    /**
     *
     * 功能描述: 将byte转为16进制
     *
     * @param:  * @param bytes
     * @return: java.lang.String
     * @auther: Aaron
     * @date: 2018/7/10 10:56
     */
    private static String byte2Hex(byte[] bytes){

        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for(int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }



}