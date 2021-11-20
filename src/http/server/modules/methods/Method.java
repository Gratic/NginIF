package http.server.modules.methods;

import http.server.modules.header.HttpHeader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

public interface Method {

    void processMethod(HttpHeader header, InputStream inputStream, OutputStream outputStream);

}
