package com.tangqi.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class UtilStream2String {


    public UtilStream2String(){

    }

    public static String Stream2String(InputStream is) throws Exception{

        ByteArrayOutputStream baos=new ByteArrayOutputStream();


        int len=-1;
        byte[] bys = new byte[1024];
        while ((len=is.read(bys))!=-1){
            baos.write(bys,0,len);
        }
       is.close();
        String content = new String(baos.toByteArray(),"gbk");
        return content;
    }
}
