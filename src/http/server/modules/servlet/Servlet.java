package http.server.modules.servlet;

import http.server.modules.header.HttpHeader;

import java.io.BufferedReader;
import java.io.OutputStream;

/**
 * "Servlet", represents a dynamic page.
 *
 * A servlet is responsible to send the header and the body into the outputStream.
 */
public interface Servlet {
    void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream);
}
