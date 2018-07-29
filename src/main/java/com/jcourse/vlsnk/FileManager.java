package com.jcourse.vlsnk;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileManager {

    File main;
    List<File> filesInDir = new ArrayList<>();
    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    public FileManager(String name) {
        File f = new File(name);
        this.main = f;
    }

    public void getFiles(){
        List<File> fileList = new ArrayList<>();
        List<File> dirList = new ArrayList<>();

        File[] files = main.listFiles();
        if(files == null) return;
        for (File s : files) {
            if (s.isDirectory()) dirList.add(s);
            if (s.isFile()) fileList.add(s);
        }
        dirList.addAll(fileList);
        this.filesInDir = dirList;
    }

    public byte[] getAnswer() throws IOException {
        if (main.isDirectory()) {
            return getHtmlFileText().getBytes();
        }
        if (main.isFile()) {
            return Files.readAllBytes(Paths.get(main.getAbsolutePath()));
        }
        return null;
    }


    public String getHtmlFileText(){

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(getHtmlFile()))) {
            CharBuffer charBuffer = CharBuffer.allocate(8*1024);
            while(true) {
                charBuffer.clear();
                reader.read(charBuffer);
                sb.append(charBuffer.array());
                if (reader.read(charBuffer) < 0) {
                    charBuffer.clear();
                    break;
                } else continue;
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File getHtmlFile(){
        File file = new File(main.getAbsolutePath(),"index.html");
        if (!exist(file.getAbsolutePath())) {
            HtmlGenerator.getHtmlFile(main, filesInDir);
        }
        return file;
    }


    public static boolean exist(String s){
        File f = new File(s);
        return f.exists() ? true : false;
    }
}
