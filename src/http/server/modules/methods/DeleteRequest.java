package http.server.modules.methods;

import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;

import java.io.BufferedReader;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * DELETE method.
 *
 * Method is restricted to /resources/uploads diretory.
 * If this method is called with a resource in this path, it can delete it.
 */
public class DeleteRequest implements HttpMethod {
    @Override
    public void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream) {
        Path path = Path.of("resources" + header.getResource()).toAbsolutePath().normalize();
        Path pRestraint = Path.of("resources/uploads").toAbsolutePath().normalize();

        HttpStatusCode httpStatusCode;
        if (path.startsWith(pRestraint)) {
            File f = path.toFile();

            f.delete();
            httpStatusCode = HttpStatusCode.ACCEPTED_202;
        } else {
            httpStatusCode = HttpStatusCode.FORBIDDEN_403;
        }

        ResponseHttpHeader responseHttpHeader = new ResponseHttpHeader(httpStatusCode);
        responseHttpHeader.write(outputStream);
    }
}
