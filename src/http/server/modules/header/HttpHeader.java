package http.server.modules.header;

import http.server.modules.MIME.MIMEType;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    private final Map<String, Object> parameters;
    private MIMEType contentType = null;

    public HttpHeader() {
        parameters = new HashMap<>();
    }

    public void parseHeader(BufferedReader in) {
        String str;

        try {
            str = in.readLine();
            if (str != null && !str.equals("")) {
                String[] arguments = str.split(" ");
                parameters.put("method", arguments[0]);
                if (arguments[1].equals("/")) {
                    parameters.put("resource", "/index.html");
                } else {
                    parameters.put("resource", arguments[1]);

                    String[] get_params = arguments[1].split("\\?");
                    if (get_params.length == 2) {
                        Map<String, String> get_param = new HashMap<>();

                        String[] params = get_params[1].split("&");
                        for (String param : params) {
                            String[] keyValue = param.split("=");
                            get_param.put(keyValue[0], keyValue[1]);
                        }

                        parameters.put("get_parameters", get_param);
                    }
                }
                parameters.put("version", arguments[2]);
                System.out.println(str);
            }

            while (str != null) {
                str = in.readLine();

                if (str.equals("")) break;

                System.out.println(str);
                String[] arguments = str.split(":", 2);
                switch (arguments[0].strip()) {
                    case "Content-Type" -> {
                        contentType = new MIMEType();
                        contentType.setMime(arguments[1].strip());
                    }
                    default -> parameters.put(arguments[0].strip(), arguments[1].strip());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMethod() {
        return (String) parameters.get("method");
    }

    public String getResource() {
        return ((String) parameters.get("resource")).split("\\?")[0];
    }

    public MIMEType getResourceType() {
        if (getResource().contains(".")) {
            String[] arguments = getResource().split("\\.");

            if (arguments.length > 1) {
                MIMEType type = new MIMEType();
                type.setExtension("." + arguments[arguments.length - 1]);
                return type;
            }
        }

        return null;
    }

    public void put(String key, Object value) {
        parameters.put(key, value);
    }

    public void setContentType(MIMEType type) {
        this.contentType = type;
    }

    public MIMEType getContentType() {
        return contentType;
    }

    public long getContentLength() {
        if (!parameters.containsKey("Content-Length"))
            return -1;

        return Long.parseLong((String) parameters.get("Content-Length"));
    }

    public void setContentLength(long length) {
        parameters.put("Content-Length", String.valueOf(length));
    }

    public boolean isResourceFound() {
        File f = new File("resources" + getResource());

        return f.exists();
    }

    public boolean isServlet() {
        return getResource().startsWith("/servlet");
    }

    public String getServletRoute() {
        return getResource().replaceFirst("/servlet", "");
    }

    public boolean hasGetParameters() {
        return parameters.containsKey("get_parameters");
    }

    public Map<String, String> getGetParameters() {
        return (Map<String, String>) parameters.get("get_parameters");
    }

    public String getParameter(String key) {
        String value = null;

        if (hasGetParameters()) {
            value = getGetParameters().getOrDefault(key, null);
        }

        return value;
    }
}
