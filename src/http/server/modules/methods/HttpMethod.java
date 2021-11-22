package http.server.modules.methods;

import http.server.modules.header.HttpHeader;

import java.io.BufferedReader;
import java.io.OutputStream;

/**
 * Interface to represent a HTTP Method.
 */
public interface HttpMethod {

    /**
     * Design Pattern Action.
     * @param header the request header.
     * @param input the input of the socket.
     * @param outputStream the output of the socket.
     */
    void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream);

}
