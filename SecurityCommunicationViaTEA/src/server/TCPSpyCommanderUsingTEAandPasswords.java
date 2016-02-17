package server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package project2task1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import util.DataCenter;
import util.Helper;
import util.PasswordHash;
import util.TEA;

/**
 * This class servers as the socket server side. 
 * @author qiuyi
 */
public class TCPSpyCommanderUsingTEAandPasswords {
    public static void main (String args[]) {
        ServerSocket server = null;
        String publicKey;
        Scanner in = new Scanner(System.in);
        try {
            
            int port = 8000;
            System.out.println("Enter symmetric key for TEA (taking first sixteen bytes):");
            publicKey = in.next();
            System.out.println("Waiting for spies to visit...");
            server = new ServerSocket(port);
            //Listen to the localhost and port 8000. 
            while (true) {                
                //Keeping listening and once get a client, assign a new thread to 
                //this client socket.
                Socket client = server.accept();
                Connection connection = new Connection(client, publicKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (Exception e) {
            }
        }
    }
}

/**
 * This class extends Thread class and handle one client socket 
 * communication handling.
 * @author qiuyi
 */
class Connection extends Thread {
    //Count to indicate the number of visit.
    private static DataCenter dataCenter = new DataCenter();
    private static int count = 1;
    private Socket client = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private String publicKey;
    private TEA tea;
    private byte[] secret = new byte[4096];
    private byte[] plain;
    
    /**
     * Constructor with socket client and TEA public key.
     * @param client
     * @param publicKey 
     */
    public Connection(Socket client, String publicKey) {
        this.client = client;
        this.publicKey = publicKey;
        try {
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
            this.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            tea = new TEA(publicKey.getBytes());
            //System.out.println("Receiving message....");
            Helper helper = new Helper(tea, in, out);
            String ret = helper.readData(secret);
            //System.out.println(ret)
            String ack = validateSpy(ret);
            helper.writeData(ack);
        } catch (Exception e) {
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
     /**
     * This method is to check whether this spy login correctly.
     * @param plain
     * @return 
     */
    private String validateSpy(String signal) {
        String ret;
        if (signal != null) {
            String[] info = signal.split(":");
            //System.out.println(signal);
            String username = info[0];
            String pwd = info[1];
            String location = info[2];
            Map<String, List<String>> map = dataCenter.getData();
            if (map.get(username) == null) {
                ret = "Not a valid user-id or password.";
                System.out.println("Got visit " + (count++) + " from " + username +
                        ".Illegal Password attempt. This may be an attack.");
            } else {
                String salt = map.get(username).get(0);
                String pwdHash = map.get(username).get(1);
                PasswordHash passwordHash = new PasswordHash();
                if (passwordHash.hash(salt+pwd).equals(pwdHash)) {
                    ret = "Thank you. Your location was securely "
                            + "transmitted to Intelligence Headquarters.";
                    System.out.println("Got visit " + (count++) + " from " + username);
                    dataCenter.writeFile(username, location);
                } else {
                    ret = "Not a valid user-id or password.";
                    System.out.println("Got visit " + (count++) + " from " + username +
                        ".Illegal Password attempt. This may be an attack.");
                }
            }
        } else {
            ret = "Illegal";
            System.out.println("Got visit " + (count++) +
                    " illegal symmetric key used. This may be an attack.");
        }
        return ret;
    }
    
}
