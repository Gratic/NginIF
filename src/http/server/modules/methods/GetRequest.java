package http.server.modules.methods;

import http.server.modules.MIME.MIMEType;
import http.server.modules.header.HttpHeader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

public class GetRequest implements Method {
    @Override
    public void processMethod(HttpHeader header, PrintWriter out, BufferedReader in) {

        String resource = header.getResource();
        System.out.println(resource);

        MIMEType type = header.getResourceType();

        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: "+type.getMime());
        out.println("Server: Bot");
        out.println("");

        // Send the HTML page
        switch (type.getMime())
        {
            case "text/html" ->
                {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("resources" + header.getResource()));
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
                        BufferedImage image = ImageIO.read(new File("resources" + header.getResource()));
                        out.print(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        }

    }
}
