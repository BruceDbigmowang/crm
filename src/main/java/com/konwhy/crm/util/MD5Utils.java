package com.konwhy.crm.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5Utils {
    public static SimpleHash addMD5(String password){
        String salt = "abc";
        int hashIterations = 6;

        //Md5Hash md5Hash = new Md5Hash(password , salt , hashIterations);
        SimpleHash simpleHash = new SimpleHash("md5" , password , salt , hashIterations);
        System.out.println(simpleHash);
        return simpleHash;
    }
}
