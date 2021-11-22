package http.server.modules.methods;

import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;

import java.io.BufferedReader;
import java.io.File;
import java.io.OutputStream;

/**
 * Basic HEAD implementation.
 */
public class HeadRequest implements HttpMethod {
    @Override
    public void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream) {
        File f = new File("resources" + header.getResource());

        ResponseHttpHeader responseHttpHeader = GetRequest.getHeader(HttpStatusCode.OK_200, f.length(), header.getContentType());
        responseHttpHeader.write(outputStream);
    }
}
