/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import baseClasses.Spielstand;

/**
 *
 * @author eikes
 * 
 * client used to send Spielstand to the server and get an up to date
 * version back
 */

public class gameClientThread extends Thread{
    private Socket gameSocket;
    private String hostName;
    private int portnum;
    private InetSocketAddress host;
    private InetAddress hostAddress;
    
    public gameClientThread(String hostName, int portnum){
        try{
            this.hostName = hostName;
            this.portnum = portnum;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        try{
        hostAddress = InetAddress.getByName(hostName);
        this.host = new InetSocketAddress(hostAddress, portnum);
        gameSocket = new Socket(hostName,portnum);

        gameSocket.setSoTimeout(30000);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(gameSocket.getInputStream()));
        
        PrintWriter out = new PrintWriter(gameSocket.getOutputStream(), true);
        
        while(!Spielstand.getInstance().endOfGame()){
            update(in,out);
        }
        out.close();
        in.close();
        gameSocket.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void update(BufferedReader in, PrintWriter out){
        if(gameSocket.isConnected()){
            String message = "Eike ist der Boss";
            try{
                out.print(message + "\r\n");
                out.flush();
                
                String reply = in.readLine();
                System.out.println(reply);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            /*try{
                gameClientOutputThread outPut = new gameClientOutputThread(in);
                outPut.start();
            }
            catch(Exception e){
                System.out.println("error while trying to write message to "
                        + "server");
                e.printStackTrace();
            }
            int len;
            byte[] b = new byte[100];
            try{
                while((len = in.read(b)) != -1){
                    String message = new String(b);
                    System.out.println(message);
                }   
            }
            catch(Exception e){
                e.printStackTrace();
            }*/
        }
        else{
            try{
                gameSocket.connect(host);
            
            }
            catch(UnknownHostException e){
                System.out.println("failed to connect to host: " + 
                        host.getHostName() + "at" + host.getAddress());
                e.printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
        }
    }
   
}
