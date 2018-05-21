package com.tangqi.safecenter.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 唐琦
 * @version $Rev$1
 * @des     用于操作流的工具类
 */
public class StreamUtil {

    /**
     * 用于把输入流转换成字符串的静态方法
     * @param is 字节输入流
     * @return  转换成的字符串。返回为null代表异常。
     */
    public static String stream2String(InputStream is){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] bys = new byte[1024];
        int len = -1;
        try {
            while ((len=is.read(bys))!=-1){
                bos.write(bys,0,len);
                return bos.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            try {
                is.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}
