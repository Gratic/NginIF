package http.server.modules.methods;

import http.server.modules.header.HttpHeader;
import http.server.modules.header.HttpStatusCode;
import http.server.modules.header.ResponseHttpHeader;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;

public class PutRequest implements Method {
    @Override
    public void processMethod(HttpHeader header, BufferedReader input, OutputStream outputStream) {
        Path path = Path.of("resources" + header.getResource()).toAbsolutePath().normalize();
        Path pRestraint = Path.of("resources/uploads").toAbsolutePath().normalize();

        File f = path.toFile();
        HttpStatusCode statusCode;

        if (path.startsWith(pRestraint)) {
            try {
                long contentLength = header.getContentLength();

                if (contentLength != -1) {
                    char[] body = new char[(int) contentLength];

                    if (input.ready()) {
                        input.read(body, 0, (int) contentLength);
                        if (f.exists()) {
                            FileReader fr = new FileReader(f);
                            char[] existingBody = new char[(int) f.length()];
                            fr.read(existingBody, 0, (int) f.length());
                            fr.close();

                            if (Arrays.equals(body, existingBody)) {
                                statusCode = HttpStatusCode.NO_CONTENT_204;
                            } else {
                                FileWriter fw = new FileWriter(f);
                                fw.write(body);
                                fw.flush();
                                fw.close();

                                statusCode = HttpStatusCode.OK_200;
                            }
                        } else {
                            f.getParentFile().mkdirs();
                            f.createNewFile();

                            FileWriter fw = new FileWriter(f);
                            fw.write(body);
                            fw.flush();
                            fw.close();

                            statusCode = HttpStatusCode.CREATED_201;
                        }
                    } else {
                        statusCode = HttpStatusCode.BAD_REQUEST_400;
                    }
                } else {
                    statusCode = HttpStatusCode.BAD_REQUEST_400;
                }
            } catch (IOException e) {
                e.printStackTrace();
                statusCode = HttpStatusCode.INTERNAL_SERVER_ERROR_500;
            }
        } else {
            statusCode = HttpStatusCode.FORBIDDEN_403;
        }

        ResponseHttpHeader responseHttpHeader = new ResponseHttpHeader(statusCode);
        responseHttpHeader.write(outputStream);
    }
}
