package http.server.modules.methods;

import http.server.modules.header.HttpHeader;

import java.io.BufferedReader;
import java.io.OutputStream;

public interface Method {

    void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream);

}
