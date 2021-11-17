package http.server.methods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class GetRequest implements Method {
    @Override
    public void processMethod(Map<String, Object> request, PrintWriter out, BufferedReader in) {
        out.println("HTTP/1.0 200 OK");
        out.println("Content-Type: text/html");
        out.println("Server: Bot");
        // this blank line signals the end of the headers
        out.println("");
        // Send the HTML page
        try {
            BufferedReader reader = new BufferedReader(new FileReader("resources" + request.get("resource")));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                out.println(currentLine);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
