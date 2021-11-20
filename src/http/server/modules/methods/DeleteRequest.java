package http.server.modules.methods;

import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;

import java.io.BufferedReader;
import java.io.File;
import java.io.OutputStream;

public class DeleteRequest implements Method {
    @Override
    public void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream) {
        String path = "resources" + header.getResource();
        File f = new File(path);

        f.delete();

        ResponseHttpHeader responseHttpHeader = new ResponseHttpHeader(HttpStatusCode.ACCEPTED_202);
        responseHttpHeader.write(outputStream);
    }
}
