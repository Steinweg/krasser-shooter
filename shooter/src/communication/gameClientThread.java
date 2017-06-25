/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import com.jme3.math.Vector3f;

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
    private static SpielstandWriter spielstandWriter;
    
    public gameClientThread(String hostName, int portnum){
        spielstandWriter = new SpielstandWriter(
                Spielstand.getInstance().toString());
        
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

        gameSocket.setSoTimeout(3000);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(gameSocket.getInputStream()));
        
        PrintWriter out = new PrintWriter(gameSocket.getOutputStream(), true);
        
        /*
        while(!Spielstand.getInstance().endOfGame()){
            update(in,out);
        }
        */
        out.close();
        in.close();
        gameSocket.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void update(BufferedReader in, PrintWriter out){
        String reply;
        String message;
        if(gameSocket.isConnected()){
            message = Spielstand.getInstance().toString();
            try{
                out.print(message + "\r\n");
                out.flush();                
                reply = in.readLine();
                spielstandWriter.nextSpielstand(reply);
                Spielstand.getInstance().renew(spielstandWriter);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public static class SpielstandWriter{
        private String myself;
        private String spielstand;
        
        private SpielstandWriter(String spielstand){
            System.out.println(spielstand);
            String[] splitted = spielstand.split("\\n",2);
            System.out.println(splitted.length);
            myself = splitted[0];
            System.out.println(myself);
            this.spielstand = spielstand;
        }
        
        private void nextSpielstand(String spielstand){
            //System.out.println(spielstand);
            String[] splitted = spielstand.split("\\n",2);
            if(!myself.equals(splitted[0])){
            //    throw new IllegalArgumentException();
            }
            System.out.println(splitted[0]);
            //System.out.println(spielstand);
//this.spielstand = splitted[1];   
        } 
        
        public Vector3f getPosition(String playerName){
            int index = spielstand.indexOf("[" + playerName +  ",") + 
                    ("[" + playerName + ",").length();
            //System.out.println(index);
            try{
                int startIndexY = spielstand.indexOf(",",index) + 1;
                int startIndexZ = spielstand.indexOf("," , startIndexY);
                //System.out.println(spielstand.substring(
                  //      startIndexY, startIndexZ));
                //float y = Float.parseFloat(spielstand.substring(
                //        startIndexY, startIndexZ -1));
            }
            catch(NumberFormatException e){
                e.printStackTrace();
            }
            return new Vector3f(0,0,0);
        }
        
        /*
        public int getDeathCount(String playerName){
            
        }
        */
    }
   
}
