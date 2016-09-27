/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scantranx.dto;

/**
 *
 * @author Ahmed
 */

import com.scantranx.core.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
 
public class Util {        
      
      
       public static String hash(String password) throws Exception {
        try {
            MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            byte[] passwordBytes = password.getBytes();
            byte[] digest = sha512.digest(passwordBytes);
            StringBuilder stringBuilder = new StringBuilder();
            String s;
            for (byte b : digest) {
                s = Integer.toHexString((int) b & 0xff).toUpperCase();
                if (s.length() == 1) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException ex) {
            
            Log.l.errorLog.error(ex.getMessage());
            throw new Exception("NoSuchAlgorithmException");
        }
    }
      
}
