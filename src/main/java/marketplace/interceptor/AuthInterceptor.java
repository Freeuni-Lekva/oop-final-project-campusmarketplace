package marketplace.interceptor;

import marketplace.annotation.Secure;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Collection;
import java.util.Map;

@WebFilter(urlPatterns = {"/*"})
public class AuthInterceptor implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Cast the request/response into HttpServletRequest/HttpServletResponse
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Read the annotation from the class
        Secure annotation;
        try {
            annotation = getAnnotation(httpRequest);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // If the method is secure and the user is not logged in (no "user" attribute in the session),
        // redirect to the login page.
        if (annotation != null && annotation.value() && httpRequest.getSession().getAttribute("user") == null) {
            httpResponse.sendRedirect("/login");
            return;
        }

        // Otherwise, continue with the filter chain (the request will be forwarded to the intended destination).
        chain.doFilter(request, response);
    }

    public Secure getAnnotation(HttpServletRequest request) throws ClassNotFoundException {
        // Get the request URI
        String uri = request.getRequestURI();

        // Get the servlet context
        ServletContext context = request.getServletContext();

        // Get all the servlet registrations
        Map<String, ? extends ServletRegistration> servletRegistrations = context.getServletRegistrations();

        // Loop through all the servlet registrations to find the one that matches the URI
        ServletRegistration registration = null;
        for (ServletRegistration currentRegistration : servletRegistrations.values()) {
            Collection<String> mappings = currentRegistration.getMappings();
            if (mappings.contains(uri)) {
                // Found the servlet mapped to the URI
                registration = currentRegistration;
                break;
            }
        }

        // If no servlet was found, return null
        if (registration == null) {
            return null;
        }

        // Get the servlet class
        Class<?> servletClass = Class.forName(registration.getClassName());

        // Get the annotation on the servlet class
        return servletClass.getAnnotation(Secure.class);
    }

    @Override
    public void destroy() {}
}