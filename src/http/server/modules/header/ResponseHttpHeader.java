package http.server.modules.header;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Daughter class of HttpHeader. This class purpose is to represent the response header.
 *
 * Only a subgroup of HttpStatusCode are implemented.
 */
public class ResponseHttpHeader extends HttpHeader {
    private final HttpStatusCode statusCode;

    public ResponseHttpHeader(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * This method write the header onto the outputStream.
     *
     * After this method the body can be written onto the outputStream.
     *
     * @param outputStream the output stream.
     */
    public void write(OutputStream outputStream) {
        PrintWriter out = new PrintWriter(outputStream);

        out.print("HTTP/1.0 ");

        switch (statusCode) {
            case OK_200 -> out.println("200 OK");
            case CREATED_201 -> out.println("201 CREATED");
            case ACCEPTED_202 -> out.println("202 ACCEPTED");
            case NO_CONTENT_204 -> out.println("204 NO_CONTENT");

            case BAD_REQUEST_400 -> out.println("400 BAD_REQUEST");
            case FORBIDDEN_403 -> out.println("403 FORBIDDEN");
            case NOT_FOUND_404 -> out.println("404 NOT_FOUND");

            case INTERNAL_SERVER_ERROR_500 -> out.println("500 INTERNAL_SERVER_ERROR");

        }

        if (getContentType() != null) {
            out.println("Content-Type: " + getContentType().getMime());
        }

        if (getContentLength() != -1) {
            out.println("Content-Length: " + getContentLength());
        }

        out.println("Server: NginIF v1");
        out.println("");

        out.flush();
    }
}
