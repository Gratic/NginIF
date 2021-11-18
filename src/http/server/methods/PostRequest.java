package http.server.methods;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PostRequest implements Method {
    @Override
    public void processMethod(Map<String, Object> request, PrintWriter out, BufferedReader in) {
        String str;
        Map<String, String> post = new HashMap<>();
        switch ((String) request.get("Content-Type")) {
            case "application/x-www-form-urlencoded" -> {
                try {
                    if (in.ready()) {
                        int contentLength = Integer.parseInt((String) request.get("Content-Length"));
                        char[] body = new char[contentLength];
                        int readContent = in.read(body, 0, contentLength);
                        System.out.println(body);
                        str = String.valueOf(body);
                        String[] parameters = str.split("&");
                        for (int i = 0; i < parameters.length; i++) {
                            String[] arguments = parameters[i].split("=");
                            post.put(arguments[0].strip(), arguments[1].strip());
                        }
                        request.put("parameters", post);
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


        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        out.println("Server: Bot");
        out.println("");
    }
}
