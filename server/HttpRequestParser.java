package server;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Defines a class that parses the incoming HTTP request from the client
 */
public class HttpRequestParser {
    
    /**
     * Defines a method which reads the client request line by line
     * and parses the client request line into three parts
     * @param input is the BufferedReader input from the client
     * @return a validated HttpRequest with all the essential parts
     */
    public HttpRequest parseRequest(BufferedReader input) {
        try {
            String message = input.readLine();
            if (message == null || message.isEmpty()) return null;

            String[] tokens = message.trim().split("\\s+");

            // If tokens does not contain the method, path, and version, then it is not a valid request
            if (tokens.length < 3) return null;

            String method = tokens[0];
            String path = tokens[1];
            String version = tokens[2];

            if (!isValidMethod(method) || !isValidVersion(version) || !isValidPath(path)) {
                return null;
            }

            return new HttpRequest(method, path, version);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Defines a method which checks whether the client made a GET request or not
     * @param method is the type of HTTP method the client makes
     * @return true if method is GET, else false
     */
    private boolean isValidMethod(String method) {
        return "GET".equalsIgnoreCase(method);
    }

    /**
     * Defines a method which checks whether the path is valid or malformed
     * @param path the uri of the resource
     * @return true if path is valid, else false
     */
    private boolean isValidPath(String path) {
        String pattern = ("^[a-zA-Z0-9._~!$&'()*+,;=:@%/?#\\[\\]\\-{}\"']+$");
        return path.matches(pattern);
    }

    /**
     * Defines a method which checks for a valid HTTP version in the request
     * @param version is the HTTP version the client uses
     * @return true if the version is HTTP 1.0 or HTTP 1.1, else false
     */
    private boolean isValidVersion(String version) {
        return "HTTP/1.0".equalsIgnoreCase(version) || "HTTP/1.1".equalsIgnoreCase(version);
    }
}
