package http.server.modules.servlet;

import http.server.modules.MIME.MIMEType;
import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class AdderServlet implements Servlet {
    @Override
    public void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream) {
        HttpStatusCode statusCode;

        String body = "";

        if (header.hasGetParameters()) {
            int a = Integer.parseInt(header.getParameter("a"));
            int b = Integer.parseInt(header.getParameter("b"));

            int c = a + b;

            statusCode = HttpStatusCode.OK_200;

            body = body.concat("<!doctype html>\n" +
                    "<html>\n" +
                    "\n" +
                    "<head>\n" +
                    "    <title>Adding two numbers</title>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>" +
                    "<p>The result is " + c + "</p>\n" +
                    "<p><a href=\"../adderServer.html\">Nouveau calcul</a></p>" +
                    "</body>\n" +
                    "\n" +
                    "</html>");
        } else {
            statusCode = HttpStatusCode.INTERNAL_SERVER_ERROR_500;
        }

        ResponseHttpHeader responseHttpHeader = new ResponseHttpHeader(statusCode);

        if (!body.equals("")) {
            responseHttpHeader.setContentLength(body.length());
            MIMEType type = new MIMEType();
            type.setExtension("html");
            responseHttpHeader.setContentType(type);
        }

        responseHttpHeader.write(outputStream);

        if (!body.equals("")) {
            PrintWriter pw = new PrintWriter(outputStream);
            pw.println(body);
            pw.flush();
        }
    }
}
