package http.server.modules.methods;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;

public interface Method {

    void processMethod(Map<String, Object> request, PrintWriter out, BufferedReader in);

}
