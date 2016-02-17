/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class file is use MD5 hash method to encrypt username and password.
 * Refer to my code in project1 Task1.
 * @author qiuyi
 */
public class PasswordHash {
    private MessageDigest messageDigest;
    /**
     * This method is to hash the password and salt.
     * @param target
     * @return 
     */
    public String hash(String target) {
        String ret;
        byte[] targetByteArray = target.getBytes();
        try {
            
            //Get the accordingly messageDigest based on the hash method.
            messageDigest = MessageDigest.getInstance("MD5");
           
            //Call the message function to get the result byte array.
            messageDigest.update(targetByteArray);
            
            byte[] resultByteArray = messageDigest.digest();
            
            ret = getHexString(resultByteArray);
                
        }catch (NoSuchAlgorithmException e) {
            return null;
        } catch (Exception exception) {
            return null;
        }
        return ret;
    }
        /**
     * This method is to get the hex format hashes.
     * @param b
     *          byte[] that indicates the result byte array 
     * @return
     *          Result that indicates the hex format hash
     * @throws Exception 
     */
    private String getHexString(byte[] b) throws Exception {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            ret += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return ret;
    }
}
