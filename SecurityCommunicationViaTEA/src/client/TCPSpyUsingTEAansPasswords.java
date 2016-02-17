package client;

//package project2task1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import util.Helper;
import util.TEA;

/**
 * This class servers as the client side and interact with 
 * the spy with username, password and location as information sent 
 * to the server side.
 * @author qiuyi
 */
public class TCPSpyUsingTEAansPasswords {
    public static void main (String args[]) {
        Socket client = null;
        String publicKey;
        BufferedReader stdin;
        TEA tea;
        StringBuilder sb = new StringBuilder();
        try {
            stdin = new BufferedReader(new InputStreamReader(System.in));
            //Create a socket client and connect to the server.
            int port = 8000;
            client = new Socket("localhost", port);
            //Get the socket out and in communication pipe.
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("Enter symmetric key for TEA (taking first sixteen bytes):");
            publicKey = stdin.readLine();
            System.out.println("Enter your ID: ");
            sb.append(stdin.readLine()).append(":");
            System.out.println("Enter your password: ");
            sb.append(stdin.readLine()).append(":");
            System.out.println("Enter your location: ");
            sb.append(stdin.readLine());
            
            //Initiliaze TEA and pass key and string to be encrypted.
            tea = new TEA(publicKey.getBytes());
            //System.out.println(sb.toString());
            Helper helper = new Helper(tea, in, out);
            helper.writeData(sb.toString());
            byte[] res = new byte[4096];
            String ret = helper.readData(res);
            if (ret == null) {
                throw new Exception("An exception occur.....");
            } else {
                System.out.println(ret);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
     }
}
