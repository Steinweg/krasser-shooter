/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooterserver;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author eikes
 */
 
public class ShooterServer
{
 
    private static Socket socket;
 
    public static void main(String[] args)
    {
        ServerSocket serverSocket;
        try{
            int port = 19999;
            serverSocket = new ServerSocket(port);
            BufferedReader in = null;
            PrintWriter out = null;
            //Server is running always. This is done using this while(true) loop
            while(true){
                try{
                    socket = serverSocket.accept();
                    in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                
                    out = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(),"8859_1"), true);
                
                    String message;
                    while((message = in.readLine()) != null){
                        System.out.println("red: " + message);
                        out.print(message + "\r\n");
                        out.flush();
                    }
                /*
                outPutThread out = new outPutThread(in);
                out.start();
                while(!out.recievedMessage){}
                String message = out.getMessage();
                out.recievedMessage = true;
                
                //Sending the response back to the client.
                
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(message);
                System.out.println("Message sent to the client is " + message);
                bw.flush();
                if(zaehler == 1000){
                    bw.write("");
                    break;
                }*/
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally{
                    if(socket != null){
                        socket.close();
                    }
                    if(in != null){
                        in.close();
                    }
                    if(out != null){
                        out.close();
                    }
                }
            }
        }
         catch(Exception e){
            
        }
    }
}