package com.jcourse.vlsnk;

import java.io.*;
import java.net.URLEncoder;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HtmlGenerator {

    private static final String start = "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Catalog</title>\n" +
            "</head>\n<body>\n";
    private static final String end = "    </table>\n</body>\n</html>\n";
    private final static String htmlType = "text/html";
    File main;
    List<File> filesInDir = new ArrayList<>();
    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");


    public HtmlGenerator(String string) {
        File f = new File(string);
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

    public File getHtmlFile(){

        File file = new File(main.getAbsolutePath(),"index.html");
        if (!exist(file.getAbsolutePath())) {
            getFiles();
            try (FileWriter fw = new FileWriter(file)) {
                fw.write(start);
                fw.write("        <header> " + main.getPath() + "</header>\n    <table>\n");
                fw.write("        <tr><td> <a href=\"" +"..\">" + main.getParent() + "</a></td>" +
                        "            <td>" + main.length() + "</td>\r" +
                        "            <td>" + formatter.format(new Date(main.lastModified())) + "</td>\n" +
                        "        </tr>\n");
                for (File f : filesInDir) {

                    fw.write("        <tr><td> <a href=\"" +
                            getLink(f) + "\">" + f.getName() + "</a></td>" +
                            "            <td>" + f.length() + "</td>\r" +
                            "            <td>" + formatter.format(new Date(f.lastModified())) + "</td>\n" +
                            "        </tr>\n");

                }
                fw.write(end);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean exist(String s){
        File f = new File(s);
        return f.exists() ? true : false;
    }

    String getLink(File f) throws IOException {
        String name = URLEncoder.encode(f.getName(), "ASCII");
        if (f.isDirectory()) return name + "\\";
        return name;
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

    public String getContent() {
        return htmlType;
    }
}
