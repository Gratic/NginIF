package http.server.modules.servlet;

import http.server.modules.header.HttpHeader;

import java.io.BufferedReader;
import java.io.OutputStream;

public interface Servlet {
    void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream);
}
