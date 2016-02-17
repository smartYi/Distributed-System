/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Map;

/**
 *
 * @author qiuyi
 */
public class Helper {
    private TEA tea;
    private DataOutputStream out;
    private DataInputStream in;

    public Helper(TEA tea, DataInputStream in, DataOutputStream out) {
        this.tea = tea;
        this.in = in;
        this.out = out;
    }
    
    /**
     * This method is to encrypt data and write data through output pipe. 
     * @param target 
     */
    public void writeData(String target) {
        try {
         byte[] encrypted = tea.encrypt(target.getBytes());
         out.write(encrypted, 0, encrypted.length);
         out.flush();   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method is to decrypt data and read data from input stream.
     * @param bytes
     * @return 
     */
    public String readData(byte[] bytes) {
        byte[] ret;
        try {
            int count = in.read(bytes);
            if (count != -1) {
                byte[] res = new byte[count];
                for (int i = 0; i < count; i++) {
                    res[i] = bytes[i];
                }
                ret = tea.decrypt(res);
                if (validKey(ret)) {
                    return new String(ret);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * This method is to check the key is valid or not.
     * @param target
     * @return 
     */
    private boolean validKey(byte[] target) {
        for (byte b : target) {
            if (b > 128 || b < 0) {
                return false;
            }
        }
        return true;
    }
    
}
