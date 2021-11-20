package http.server.modules.header;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ResponseHttpHeader extends HttpHeader {
    private final HttpStatusCode statusCode;

    public ResponseHttpHeader(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void write(OutputStream outputStream) {
        PrintWriter out = new PrintWriter(outputStream);

        out.print("HTTP/1.0 ");

        switch (statusCode) {
            case OK_200 -> out.println("200 OK");
        }

        if (getContentType() != null) {
            out.println("Content-Type: " + getContentType().getMime());
        }

        if (getContentLength() != -1) {
            out.println("Content-Length: " + getContentLength());
        }

        out.println("Server: NginIF v0.1");
        out.println("");

        out.flush();
    }
}
