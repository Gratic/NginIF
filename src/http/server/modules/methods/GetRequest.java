package http.server.modules.methods;

import http.server.modules.MIME.MIMEType;
import http.server.modules.header.HttpHeader;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Map;

public class GetRequest implements Method {
    @Override
    public void processMethod(HttpHeader header, InputStream inputStream, OutputStream outputStream) {
        PrintWriter out = new PrintWriter(outputStream);

        String resource = header.getResource();
        System.out.println(resource);

        MIMEType type = header.getResourceType();

        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: "+type.getMime());
        out.println("Server: Bot");
        out.println("");

        out.flush();

        // Send the HTML page

        File f = new File("resources" + header.getResource());
        try
        {
            byte[] data = Files.readAllBytes(f.toPath());
            outputStream.write(data);

        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
