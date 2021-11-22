package http.server.modules.methods;

import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;

import java.io.BufferedReader;
import java.io.File;
import java.io.OutputStream;

public class HeadRequest implements Method {
    @Override
    public void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream) {
        File f = new File("resources" + header.getResource());

        if (header.isServlet()) {
            // TODO: faifrjezojn,rkldsnf,kloqdsin,fdiopsn,fikoer
            // TODO: faifrjezojn,rkldsnf,kloqdsin,fdiopsn,fikoer
        }

        ResponseHttpHeader responseHttpHeader = GetRequest.getHeader(HttpStatusCode.OK_200, f.length(), header.getContentType());
        responseHttpHeader.write(outputStream);
    }
}
