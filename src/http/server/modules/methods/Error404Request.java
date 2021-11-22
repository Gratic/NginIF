package http.server.modules.methods;

import http.server.modules.MIME.MIMEType;
import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * Not really a method but can be implemented as such.
 *
 * Action to take when the file doesn't exist.
 */
public class Error404Request implements HttpMethod {

    @Override
    public void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream) {
        File f = new File("resources/404.html");

        ResponseHttpHeader responseHttpHeader = new ResponseHttpHeader(HttpStatusCode.NOT_FOUND_404);
        responseHttpHeader.setContentType(new MIMEType().setExtension(".html"));
        responseHttpHeader.setContentLength(f.length());

        responseHttpHeader.write(outputStream);

        try {
            byte[] data = Files.readAllBytes(f.toPath());
            outputStream.write(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
