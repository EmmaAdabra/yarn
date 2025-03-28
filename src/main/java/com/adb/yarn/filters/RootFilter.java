/**
 * The `RootFilter` class in a Java web application filters incoming requests, handles root routes,
 * skips static resources and API endpoints, and finds the nearest valid route based on the requested
 * path.
 */
package com.adb.yarn.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class RootFilter implements Filter {

    private static final List<String> VALID_ROUTES = Arrays.asList(
            "/home",
            "/register",
            "/login",
            "/logout",
            "/dashboard",
            "/edit_profile",
            "/authenticateUser"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Normalize the path by removing trailing slashes
        String path = request.getRequestURI().replaceAll("[/]+$", "");

        // Handle root routes (e.g., "/", "//", "http://localhost:8080/")
        if (path.equals("") || path.equals(request.getContextPath())) {
            request.getRequestDispatcher("/home").forward(request, response);
            return; // Exit the filter after forwarding
        }

//        // Skip static resources and API endpoints
        if (path.startsWith("/assets") || path.startsWith("/uploads") || path.startsWith("/fetchPfp")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Handle empty path or context root
        if (path.equals("") || path.equals(request.getContextPath())) {
            request.getRequestDispatcher("/home").forward(request, response);
            return; // Exit the filter after forwarding
        }

        // Find the nearest valid route
        String nearestRoute = findNearestRoute(path);
        if (nearestRoute != null && !nearestRoute.equals(path)) {
            response.sendRedirect(nearestRoute);
            return; // Exit the filter after redirecting
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Finds the nearest valid route by progressively removing segments from the path.
     *
     * @param requestedPath The requested path.
     * @return The nearest valid route, or null if no valid route is found.
     */
    private String findNearestRoute(String requestedPath) {
        String[] segments = requestedPath.split("/");
        StringBuilder pathBuilder = new StringBuilder();

        for (int i = 1; i < segments.length; i++) { // Start from 1 to skip the empty first segment
            pathBuilder.append("/").append(segments[i]);
            String currentPath = pathBuilder.toString();

            if (VALID_ROUTES.contains(currentPath)) {
                return currentPath; // Nearest valid route found
            }
        }

        return null; // No valid route found
    }
}
