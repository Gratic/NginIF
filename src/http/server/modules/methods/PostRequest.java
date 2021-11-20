package http.server.modules.methods;

import http.server.modules.MIME.MIMEType;
import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PostRequest implements Method {
    @Override
    public void processMethod(HttpHeader header, InputStream inputStream, OutputStream outputStream) {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter out = new PrintWriter(outputStream);

        StringBuilder str = new StringBuilder();
        Map<String, String> post = new HashMap<>();
        MIMEType contentType = header.getContentType();

        switch (contentType.getMime()) {
            case "application/x-www-form-urlencoded" -> {
                try {
                    if (in.ready()) {
                        long contentLength = header.getContentLength();
                        long readTotal = 0L;
                        while (readTotal != contentLength) {
                            char[] body = new char[65312];
                            int readContent = in.read(body, (int) readTotal, 65312);

                            readTotal += readContent;

                            System.out.println(body);
                            str.append(String.valueOf(body));
                        }

                        String[] parameters = str.toString().split("&");
                        for (int i = 0; i < parameters.length; i++) {
                            String[] arguments = parameters[i].split("=");
                            post.put(arguments[0].strip(), arguments[1].strip());
                        }
                        header.put("parameters", post);
                    } else {
                        System.out.println("Je n'ai pas de body");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            default -> {
                System.out.println("post content type pas pris en charge");
            }
        }

        File postParameters = new File("resources/postParameters");
        try {
            FileWriter fileWriter = new FileWriter(postParameters);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (Map.Entry<String, String> entry : post.entrySet()) {
                printWriter.println(entry.getKey() + " : " + entry.getValue());
            }
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        ResponseHttpHeader responseHttpHeader = new ResponseHttpHeader(HttpStatusCode.OK_200);
        responseHttpHeader.setContentType(new MIMEType().setExtension(".html"));
        responseHttpHeader.write(outputStream);
    }
}
