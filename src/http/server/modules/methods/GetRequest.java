package http.server.modules.methods;

import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class GetRequest implements Method {
    @Override
    public void processMethod(HttpHeader header, InputStream inputStream, OutputStream outputStream) {
        String resource = header.getResource();
        System.out.println(resource);

        File f = new File("resources" + header.getResource());

        ResponseHttpHeader responseHttpHeader = new ResponseHttpHeader(HttpStatusCode.OK_200);
        responseHttpHeader.setContentLength(f.length());
        responseHttpHeader.setContentType(header.getContentType());
        responseHttpHeader.write(outputStream);

        try {
            byte[] data = Files.readAllBytes(f.toPath());
            outputStream.write(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
