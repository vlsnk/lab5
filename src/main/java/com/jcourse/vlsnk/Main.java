package com.jcourse.vlsnk;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Main {

    public static void main(String[] args){

        try {
            ServerSocket serverSocket = new ServerSocket(8999);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New connection ... from " + socket.getRemoteSocketAddress());
                new Thread(new FileGenerator(socket)).start();
            }
        } catch (SocketException se) {
            se.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
