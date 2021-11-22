package http.server;

import http.server.modules.servlet.AdderServlet;
import http.server.modules.servlet.Servlet;

import java.util.HashMap;
import java.util.Map;

public class Route {
    public static Map<String, Servlet> routes;

    public static void init() {
        routes = new HashMap<>();

        routes.put("/adder", new AdderServlet());
    }

    public static boolean exists(String key) {
        return routes.containsKey(key);
    }

    public static Servlet getServlet(String key) {
        return routes.get(key);
    }

}
