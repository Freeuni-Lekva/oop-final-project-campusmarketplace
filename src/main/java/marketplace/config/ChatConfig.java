package marketplace.config;

import javax.servlet.ServletContext;
import javax.websocket.server.ServerEndpointConfig;

public class ChatConfig extends ServerEndpointConfig.Configurator {
    private static ServletContext servletContext;

    public static void setServletContext(ServletContext context) {
        servletContext = context;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }
}
