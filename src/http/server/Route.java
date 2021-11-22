package http.server;

import http.server.modules.servlet.AdderServlet;
import http.server.modules.servlet.Servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic Routes implementation for dynamic pages.
 *
 * init() must be called before using any of the other methods.
 */
public class Route {
    public static Map<String, Servlet> routes;

    /**
     * Initialize routes.
     *
     * Modify here to add route to the application.
     */
    public static void init() {
        routes = new HashMap<>();

        routes.put("/adder", new AdderServlet());
    }

    /**
     * Tells if a route exist.
     *
     * @param servletResource the path to the servlet. (Should not include anything before "/servlet" and "/servlet" itself.)
     * @return True if the route exists, false otherwise.
     */
    public static boolean exists(String servletResource) {
        return routes.containsKey(servletResource);
    }

    /**
     * Return the servlet associated with the route.
     *
     * @param servletResource the path to the servlet. (Should not include anything before "/servlet" and "/servlet" itself.)
     * @return The servlet if the path to the servlet exists, otherwise null.
     */
    public static Servlet getServlet(String servletResource) {
        return routes.getOrDefault(servletResource, null);
    }

}
