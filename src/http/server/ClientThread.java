package http.server;

import http.server.modules.header.HttpHeader;
import http.server.modules.methods.*;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

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
            switch (request.getMethod()) {
                case "GET" -> {
                    if(request.isResourceFound())
                        methodToProcess = new GetRequest();
                    else
                        methodToProcess = new Error404Request();
                }
                case "POST" -> methodToProcess = new PostRequest();
                case "HEAD" -> {
                    if(request.isResourceFound())
                        methodToProcess = new HeadRequest();
                    else
                        methodToProcess = new Error404Request();
                }
                case "PUT" -> methodToProcess = new PutRequest();
                default -> {}
            }

            if (methodToProcess != null) {
                    methodToProcess.processMethod(request, input, remote.getOutputStream());
                out.flush();
            }

            remote.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
