package com.tangqi.safecenter.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * 还不会用
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Md5Util {


    private static StringBuffer sStringBuffer;

    /**给指定字符串按照md5算法去加密
     * @param ps	需要加密的密码
     */
    public static String  encoder(String ps) {
        try {
            //加盐
            String psd=ps+"fgvsdgawesdfvsdhfyuilgjnolhilguikbsdgsdght";
            //1,指定加密算法类型
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //2,将需要加密的字符串中转换成byte类型的数组,然后进行随机哈希过程
            byte[] bs = digest.digest(psd.getBytes());
//			System.out.println(bs.length);
            //3,循环遍历bs,然后让其生成32位字符串,固定写法
            //4,拼接字符串过程
            sStringBuffer = new StringBuffer();
            for (byte b : bs) {
                int i = b & 0xff;
                //int类型的i需要转换成16机制字符
                String hexString = Integer.toHexString(i);
//				System.out.println(hexString);
                if(hexString.length()<2){
                    hexString = "0"+hexString;
                }
                sStringBuffer.append(hexString);
            }

            //5,打印测试
            System.out.println(sStringBuffer.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }finally {
            return sStringBuffer.toString();
        }
    }
}