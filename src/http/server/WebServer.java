///A Simple Web Server (WebServer.java)

package http.server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Entry point of the application.
 */
public class WebServer {

    /**
     * WebServer constructor.
     *
     * This method is blocking.
     */
    protected void start() {
        ServerSocket s;

        Route.init();

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
                ClientThread clientThread = new ClientThread(remote);
                clientThread.start();
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
