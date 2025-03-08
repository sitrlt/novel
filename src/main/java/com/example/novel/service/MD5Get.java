package com.example.novel.service;

import java.security.MessageDigest;

public class MD5Get
{
    public static String getMD5(String str) throws Exception
    {
        StringBuilder MD5 =new StringBuilder();
        MessageDigest md5 = MessageDigest.getInstance("MD5");


        byte[] bytes = str.getBytes();
        byte[] digest = md5.digest(bytes);

        for(int i:digest)
        {
            int j = i;
            j = j & 0x000000ff;
            String s1 = Integer.toHexString(j);
            if(s1.length()==1)
            {
                s1="0"+s1;
            }
            MD5.append(s1);

        }
        return MD5.toString();
    }
}
