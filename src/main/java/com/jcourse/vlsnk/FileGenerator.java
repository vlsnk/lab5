package com.jcourse.vlsnk;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;

public class FileGenerator implements Runnable {

    Socket socket;
    private final static String getRequest = "GET";
    private final static String headRequest = "HEAD";

    public FileGenerator(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        while (true) {
            if (socket.isClosed()) return;
            try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                 DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

                StringBuilder sb = new StringBuilder();
                int c;
                while((c = inputStream.read())!=-1 && c!=10 && c!=13){
                    sb.append((char)c);
                }
                String data = sb.toString();
                String args[] = data.split(" ");
                String cmd = args[0].trim().toUpperCase();

                if (cmd.equals(getRequest) || cmd.equals(headRequest)) {
                    System.out.println(cmd);
                    if (args.length<2) return;

                    String name = URLDecoder.decode(args[1], "ASCII");
                    if (!FileManager.exist(name)){
                        sendNotFoundError(outputStream);
                    } else {
                        FileManager fileManager = new FileManager(name);
                        byte[] answer = fileManager.getAnswer();

                        if (answer == null) sendNotFoundError(outputStream);

                        sendHead(outputStream, answer.length, "text/html");
                        if (cmd.equals(getRequest)) sendHtmlBody(outputStream, answer);
                    }
                } else {
                    send501Error(outputStream);
                }
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendHead(DataOutputStream outputStream, int length, String contentType) throws IOException {
        outputStream.write("HTTP/1.0 200 OK\r\n".getBytes());
        outputStream.flush();
        outputStream.write(("Content-Type: " + contentType + "\r\n").getBytes());
        outputStream.write(("Content-Length: " + length + "\r\n").getBytes());
        outputStream.write("\r\n".getBytes());
        outputStream.flush();
    }

    void sendHtmlBody(DataOutputStream outputStream, byte[] s) throws IOException {
        outputStream.write(s);
        outputStream.flush();
    }

    void sendNotFoundError(DataOutputStream outputStream) throws IOException {
        outputStream.write("HTTP/1.0 404 Not Found\r\n".getBytes());
        outputStream.flush();
    }

    void send501Error(DataOutputStream outputStream) throws IOException {
        outputStream.write("HTTP/1.0 501 Not Implemented\r\n".getBytes());
        outputStream.flush();
        outputStream.write(HtmlGenerator.generateExceptionHtml("501 Not Implemented").getBytes());
        outputStream.flush();
    }
}
