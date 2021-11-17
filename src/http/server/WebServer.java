///A Simple Web Server (WebServer.java)

package http.server;

import java.io.*;
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
                Map<String,String> request = new HashMap<>();
                String str = ".";
                boolean firstLine=true;
                while (str != null && !str.equals("")) {
                    str = in.readLine();
                    if(firstLine && str!=null && !str.equals(""))
                    {
                        String[] arguments = str.split(" ");
                        request.put("method",arguments[0]);
                        request.put("resource",arguments[1]);
                        request.put("version",arguments[2]);
                    }
                    System.out.println(str);
                    firstLine=false;
                }
                // Send the response
                // Send the headers
                out.println("HTTP/1.0 200 OK");
                out.println("Content-Type: text/html");
                out.println("Server: Bot");
                // this blank line signals the end of the headers
                out.println("");
                // Send the HTML page
                BufferedReader reader = new BufferedReader(new FileReader("resources"+request.get("resource")));
                String currentLine;
                while((currentLine=reader.readLine())!=null){
                    out.println(currentLine);
                }

                reader.close();
                out.flush();
                remote.close();
            } catch (Exception e) {
                System.out.println("Error: " + e);
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
