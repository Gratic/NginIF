package http.server.modules.methods;

import http.server.modules.MIME.MIMEType;
import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic POST implementation.
 * Write a file in /resources with the parameters.
 */
public class PostRequest implements HttpMethod {
    @Override
    public void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream) {
        String str;
        Map<String, String> post = new HashMap<>();
        MIMEType contentType = header.getContentType();

        switch (contentType.getMime()) {
            case "application/x-www-form-urlencoded" -> {
                try {
                    long contentLength = header.getContentLength();
                    if (contentLength != -1) {
                        char[] body = new char[(int) contentLength];

                        input.read(body, 0, (int) contentLength);
                        str = String.valueOf(body);

                        String[] parameters = str.split("&");
                        for (String parameter : parameters) {
                            String[] arguments = parameter.split("=");
                            post.put(arguments[0].strip(), arguments[1].strip());
                        }
                        header.put("post_parameters", post);
                    } else {
                        System.out.println("Je n'ai pas de body");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            default -> System.out.println("post content type pas pris en charge");
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
