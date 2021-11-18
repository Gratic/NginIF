///A Simple Web Server (WebServer.java)

package http.server;

import http.server.modules.methods.GetRequest;
import http.server.modules.methods.Method;
import http.server.modules.methods.PostRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Example program from Chapter 1 Programming Spiders, Bots and Aggregators in
 * Java Copyright 2001 by Jeff Heaton
 * <p>
 * WebServer is a very simple web-server. Any request is responded with a very
 * simple web-page.
 *
 * @author Jeff Heaton
 * @version 1.0
 */
public class WebServer {

    /**
     * WebServer constructor.
     */
    protected void start() {
        ServerSocket s;

        System.out.println("Webserver starting up on port 3000");
        System.out.println("(press ctrl-c to exit)");
        try {
            // create the main server socket
            s = new ServerSocket(3000);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }

        System.out.println("Waiting for connection");
        for (; ; ) {
            try {
                // wait for a connection
                Socket remote = s.accept();
                // remote is now the connected socket
                System.out.println("Connection, sending data.");
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        remote.getInputStream()));
                PrintWriter out = new PrintWriter(remote.getOutputStream());


                // read the data sent. We basically ignore it,
                // stop reading once a blank line is hit. This
                // blank line signals the end of the client HTTP
                // headers.
                Map<String, Object> request = new HashMap<>();
                String str = ".";

                str = in.readLine();
                if (str != null && !str.equals("")) {
                    String[] arguments = str.split(" ");
                    request.put("method", arguments[0]);
                    request.put("resource", arguments[1]);
                    request.put("version", arguments[2]);
                    System.out.println(str);
                }

                while (str != null) {
                    str = in.readLine();

                    if (str.equals("")) break;

                    System.out.println(str);
                    String[] arguments = str.split(":", 2);
                    request.put(arguments[0].strip(), arguments[1].strip());
                }


                Method methodToProcess = null;
                switch ((String) request.get("method")) {
                    case "GET" -> {
                        methodToProcess = new GetRequest();
                    }
                    case "POST" -> {
                        methodToProcess = new PostRequest();
                    }
                }
                if (methodToProcess != null) {
                    methodToProcess.processMethod(request, out, in);
                    out.flush();
                }
                remote.close();
            } catch (Exception e) {
                System.out.println("Error: " + e);
                e.printStackTrace();
            }
        }
    }

    /**
     * Start the application.
     *
     * @param args Command line parameters are not used.
     */
    public static void main(String[] args) {
        WebServer ws = new WebServer();
        ws.start();
    }
}
