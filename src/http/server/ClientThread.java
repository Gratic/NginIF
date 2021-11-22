package http.server;

import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;
import http.server.modules.methods.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;

/**
 * Represent a single connection with a client.
 *
 * This way we can allow the server to have multiple connection at once.
 *
 * This class has the responsibility to process the request and delegate the response.
 * (Look like a controller)
 *
 * Resources accessible from the outside world are restricted to : /resources.
 * Resources modifiable from the outside world (PUT, DELETE) are restricted to : /resources/uploads.
 * Dynamic resources must have their resource path starting with /servlet.
 */
public class ClientThread extends Thread {
    private final Socket remote;

    public ClientThread(Socket socket) {
        this.remote = socket;
    }

    @Override
    public void run() {
        try {
            // remote is now the connected socket
            System.out.println("Connection, sending data.");
            BufferedReader input = new BufferedReader(new InputStreamReader(remote.getInputStream()));
            PrintWriter out = new PrintWriter(remote.getOutputStream());

            // Parse the header
            HttpHeader request = new HttpHeader();
            request.parseHeader(input);

            HttpMethod httpMethodToProcess = null;

            Path path = Path.of("resources" + request.getResource()).toAbsolutePath().normalize();
            Path pRestraint = Path.of("resources").toAbsolutePath().normalize();

            // Restrict access
            if (path.startsWith(pRestraint)) {
                // Delegate process depending on the HTTP Method.
                switch (request.getMethod()) {
                    case "GET" -> {
                        if (request.isResourceFound() ||
                                (request.isServlet() && Route.exists(request.getServletRoute())))
                            httpMethodToProcess = new GetRequest();
                        else
                            httpMethodToProcess = new Error404Request();
                    }
                    case "POST" -> httpMethodToProcess = new PostRequest();
                    case "HEAD" -> {
                        if (request.isResourceFound())
                            httpMethodToProcess = new HeadRequest();
                        else
                            httpMethodToProcess = new Error404Request();
                    }
                    case "PUT" -> httpMethodToProcess = new PutRequest();
                    case "DELETE" -> {
                        if (request.isResourceFound())
                            httpMethodToProcess = new DeleteRequest();
                        else
                            httpMethodToProcess = new Error404Request();
                    }
                    default -> {
                    }
                }

                if (httpMethodToProcess != null) {
                    httpMethodToProcess.processMethod(request, input, remote.getOutputStream());
                    out.flush();
                }
            } else {
                // Forbidden Access
                ResponseHttpHeader responseHttpHeader = new ResponseHttpHeader(HttpStatusCode.FORBIDDEN_403);
                responseHttpHeader.write(remote.getOutputStream());
            }

            remote.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
