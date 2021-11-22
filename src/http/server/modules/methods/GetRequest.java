package http.server.modules.methods;

import http.server.Route;
import http.server.modules.MIME.MIMEType;
import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;
import http.server.modules.servlet.Servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class GetRequest implements Method {
    @Override
    public void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream) {
        Path path = Path.of("resources" + header.getResource()).toAbsolutePath().normalize();

        if (header.isServlet()) {
            Servlet servlet = Route.getServlet(header.getServletRoute());
            servlet.processMethod(header, input, outputStream);
        } else {
            File f = path.toFile();
            ResponseHttpHeader responseHttpHeader = getHeader(HttpStatusCode.OK_200, f.length(), header.getContentType());
            responseHttpHeader.write(outputStream);

            try {
                byte[] data = Files.readAllBytes(f.toPath());
                outputStream.write(data);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static ResponseHttpHeader getHeader(HttpStatusCode httpStatusCode, long length, MIMEType type) {
        ResponseHttpHeader responseHttpHeader = new ResponseHttpHeader(httpStatusCode);
        responseHttpHeader.setContentLength(length);
        responseHttpHeader.setContentType(type);
        return responseHttpHeader;
    }
}
