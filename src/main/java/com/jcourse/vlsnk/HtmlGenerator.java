package com.jcourse.vlsnk;

import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class HtmlGenerator {

    private static final String start = "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Catalog</title>\n" +
            "</head>\n<body>\n";
    private static final String end = "    </table>\n</body>\n</html>\n";

    static String getLink(File f) throws IOException {
        String name = URLEncoder.encode(f.getName(), "ASCII");
        if (f.isDirectory()) return name + "\\";
        return name;
    }

    public static File getHtmlFile(File main, List<File> fileList){

        File file = new File(main.getAbsolutePath(),"index.html");
        try (FileWriter fw = new FileWriter(file)) {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
            fw.write(start);
            fw.write("        <header> " + main.getPath() + "</header>\n    <table>\n");
            fw.write("        <tr><td> <a href=\"" +"..\">" + main.getParent() + "</a></td>" +
                    "            <td>" + main.length() + "</td>\r" +
                    "            <td>" + formatter.format(new Date(main.lastModified())) + "</td>\n" +
                    "        </tr>\n");
            for (File f : fileList) {

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

        return file;
    }

    static String generateExceptionHtml(String s) {
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Error</title>\n" +
                "</head>\n<body>\n" +
                s +
                "</body>\n</html>";
    }

}
