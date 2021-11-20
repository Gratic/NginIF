package http.server.modules.methods;

import http.server.modules.MIME.MIMEType;
import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;

import java.io.*;
import java.nio.file.Files;

public class GetRequest implements Method {
    @Override
    public void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream) {
        File f = new File("resources" + header.getResource());
        ResponseHttpHeader responseHttpHeader = getHeader(f.length(), header.getContentType());
        responseHttpHeader.write(outputStream);

        try {
            byte[] data = Files.readAllBytes(f.toPath());
            outputStream.write(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ResponseHttpHeader getHeader(long length, MIMEType type) {
        ResponseHttpHeader responseHttpHeader = new ResponseHttpHeader(HttpStatusCode.OK_200);
        responseHttpHeader.setContentLength(length);
        responseHttpHeader.setContentType(type);
        return responseHttpHeader;
    }
}
