package http.server;

import http.server.modules.header.HttpHeader;
import http.server.modules.methods.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    remote.getInputStream()));
            PrintWriter out = new PrintWriter(remote.getOutputStream());

            // read the data sent. We basically ignore it,
            // stop reading once a blank line is hit. This
            // blank line signals the end of the client HTTP
            // headers.
            HttpHeader request = new HttpHeader();
            request.parseHeader(in);

            Method methodToProcess = null;
            if (request.isResourceFound()) {
                switch (request.getMethod()) {
                    case "GET" -> methodToProcess = new GetRequest();
                    case "POST" -> methodToProcess = new PostRequest();
                    case "HEAD" -> methodToProcess = new HeadRequest();
                }


            } else {
                methodToProcess = new Error404Request();
            }

            if (methodToProcess != null) {
                methodToProcess.processMethod(request, in, remote.getOutputStream());
                out.flush();
            }

            remote.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}