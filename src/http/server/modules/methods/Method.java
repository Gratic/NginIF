package http.server.modules.methods;

import http.server.modules.header.HttpHeader;

import java.io.InputStream;
import java.io.OutputStream;

public interface Method {

    void processMethod(HttpHeader header, InputStream inputStream, OutputStream outputStream);

}
