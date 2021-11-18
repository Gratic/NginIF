package http.server.methods;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

public class GetRequest implements Method {
    @Override
    public void processMethod(Map<String, Object> request, PrintWriter out, BufferedReader in) {

        String resource = (String)request.get("resource");
        System.out.println(resource);
        String type ="text/html";
        String[] splitResource = resource.split("\\.");
        switch (splitResource[1]){
            case "html" -> {
                type = "text/html";
            }
            case "png" -> {
                type = "image/png";
            }
        }
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: "+type);
        out.println("Server: Bot");
        out.println("");
        // Send the HTML page
        switch (type)
        {
            case "text/html" ->
                {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("resources" + request.get("resource")));
                        String currentLine;
                        while ((currentLine = reader.readLine()) != null) {
                            out.println(currentLine);
                        }

                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            case "image/png" ->
                {
                    try {
                        //BufferedInputStream reader = new BufferedInputStream(new FileReader("resources" + request.get("resource")));
                        BufferedImage image = ImageIO.read(new File("resources" + request.get("resource")));
                        out.print(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        }

    }
}
