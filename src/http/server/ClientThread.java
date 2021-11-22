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

            // read the data sent. We basically ignore it,
            // stop reading once a blank line is hit. This
            // blank line signals the end of the client HTTP
            // headers.
            HttpHeader request = new HttpHeader();
            request.parseHeader(input);

            Method methodToProcess = null;

            Path path = Path.of("resources" + request.getResource()).toAbsolutePath().normalize();
            Path pRestraint = Path.of("resources").toAbsolutePath().normalize();

            if(path.startsWith(pRestraint))
            {
                switch (request.getMethod()) {
                    case "GET" -> {
                        if (request.isResourceFound())
                            methodToProcess = new GetRequest();
                        else
                            methodToProcess = new Error404Request();
                    }
                    case "POST" -> methodToProcess = new PostRequest();
                    case "HEAD" -> {
                        if (request.isResourceFound())
                            methodToProcess = new HeadRequest();
                        else
                            methodToProcess = new Error404Request();
                    }
                    case "PUT" -> methodToProcess = new PutRequest();
                    case "DELETE" -> {
                        if (request.isResourceFound())
                            methodToProcess = new DeleteRequest();
                        else
                            methodToProcess = new Error404Request();
                    }
                    default -> {
                    }
                }

                if (methodToProcess != null) {
                    methodToProcess.processMethod(request, input, remote.getOutputStream());
                    out.flush();
                }
            }
            else
            {
                ResponseHttpHeader responseHttpHeader = new ResponseHttpHeader(HttpStatusCode.FORBIDDEN_403);
                responseHttpHeader.write(remote.getOutputStream());
            }



            remote.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
