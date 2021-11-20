package http.server.modules.header;

import http.server.modules.MIME.MIMEType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeader {
    private Map<String, Object> parameters;
    private MIMEType contentType = null;

    public HttpHeader()
    {
        parameters = new HashMap<>();
    }

    public void parseHeader(BufferedReader in)
    {
        String str = ".";

        try {
            str = in.readLine();
            if (str != null && !str.equals("")) {
                String[] arguments = str.split(" ");
                parameters.put("method", arguments[0]);
                if(arguments[1].equals("/"))
                {
                    parameters.put("resource", "/index.html");
                }
                else
                {
                    parameters.put("resource", arguments[1]);
                }
                parameters.put("version", arguments[2]);
                System.out.println(str);
            }

            while (str != null) {
                str = in.readLine();

                if (str.equals("")) break;

                System.out.println(str);
                String[] arguments = str.split(":", 2);
                switch (arguments[0].strip())
                {
                    case "Content-Type" -> {
                        contentType = new MIMEType();
                        contentType.setMime(arguments[1].strip());
                    }
                    default -> parameters.put(arguments[0].strip(), arguments[1].strip());
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getMethod()
    {
        return (String)parameters.get("method");
    }

    public String getResource()
    {
        return (String)parameters.get("resource");
    }

    public MIMEType getResourceType()
    {
        if(getResource().contains("."))
        {
            String[] arguments = getResource().split("\\.");

            if(arguments.length > 1)
            {
                MIMEType type = new MIMEType();
                type.setExtension("." + arguments[arguments.length-1]);
                return type;
            }
        }

        return null;
    }

    public void put(String key, Object value)
    {
        parameters.put(key, value);
    }

    public void setContentType(MIMEType type)
    {
        this.contentType = type;
    }

    public MIMEType getContentType()
    {
        return contentType;
    }

    public int getContentLength()
    {
        return Integer.parseInt((String)parameters.get("Content-Length"));
    }
}
