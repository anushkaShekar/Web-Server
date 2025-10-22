package server;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines a class that holds all the utility methods for generating an HTTP response
 */
public class HttpUtil {
    // Private variables
    public static final String END_LINE = "\r\n";
    private static Map<String, String> textMimeTypes = new HashMap<>();
    private static Map<String, String> binaryMimeTypes = new HashMap<>();

    static {
        // Mime Types for Test resources
        textMimeTypes.put("html", "text/html");
        textMimeTypes.put("txt", "text/plain");
        textMimeTypes.put("css", "text/css");
        textMimeTypes.put("js", "text/javascript");

        // Mime Types for Binary resources
        binaryMimeTypes.put("jpeg", "image/jpeg");
        binaryMimeTypes.put("jpg", "image/jpeg");
        binaryMimeTypes.put("gif", "image/gif");
        binaryMimeTypes.put("png", "image/png");
        binaryMimeTypes.put("svg", "image/svg+xml");
    }
    // Mime Types for Text resources
    public static Map<String, String> getTextMimeMap() {
        textMimeTypes.put("html", "text/html");
        textMimeTypes.put("txt", "text/plain");
        textMimeTypes.put("css", "text/css");
        textMimeTypes.put("js", "text/javascript");

        return textMimeTypes;
    }

    // Mime Types for Binary resources
    public static Map<String, String> getBinaryMimeMap() {
        binaryMimeTypes.put("jpeg", "image/jpeg");
        binaryMimeTypes.put("jpg", "image/jpeg");
        binaryMimeTypes.put("gif", "image/gif");
        binaryMimeTypes.put("png", "image/png");
        binaryMimeTypes.put("svg", "image/svg+xml");

        return binaryMimeTypes;
    }

    /**
     * Defines a method to get the current date
     * @return the current date as a String
     */
    public static String getDate() {
        return new Date().toString();
    }

    /**
     * Defines a method that gets the media type of a resource based on the file extension
     * @param extension is the part of a file path that comes after the dot (Ex. jpg, jpeg, css, etc.)
     * @return the media type as a String
     */
    public static String getContentType(String extension) {
        String contentType = null;

        if (textMimeTypes.containsKey(extension)) {
            contentType = textMimeTypes.get(extension);
        } else if (binaryMimeTypes.containsKey(extension)) {
            contentType = binaryMimeTypes.get(extension);
        }
        return contentType;
    }

    /**
     * Defines a method to extract the extension of a file
     * @param request is the parsed HTTP request from a client
     * @return the extension as a String
     */
    public static String getExtension(String request) {
        int dotIndex = request.lastIndexOf('.');
        String extension = request.substring(dotIndex + 1);

        return extension;
    }

    /**
     * Defines a method to build the HTTP response header
     * @param statusCode represents the status of the request (200, 400, 404, etc.)
     * @param description defines the status code (OK, Bad Request, Not Found, etc.)
     * @param version is the HTTP version the client uses to send the request (HTTP 1.0 or HTTP 1.1)
     * @param contentType is media type of the resource (jpg, jpeg, css, js, etc.);
     * @param len is the size of the resource
     * @return the HTTP response header as a String
     */
    public static String constructHeader(int statusCode, String description, String version, String contentType,
                                                                                             long len) {
        StringBuilder header = new StringBuilder();

        if (version == null) {
            version = "HTTP/1.1";
        }

        if (description == null) {
            description = "";
        }
        header.append(version)
              .append(" ").append(statusCode).append(" ").append(description)
              .append(END_LINE);

        // Creating the content type
        if (contentType != null && !contentType.isEmpty() && len > 0) {
            header.append("Content-Type: ").append(contentType).append(END_LINE);
        }

        // Creating content length, and date
        header.append("Content-Length: ").append(len).append(END_LINE);
        header.append("Date: ").append(getDate()).append(END_LINE);
              
        header.append(END_LINE);
        
        return header.toString();
    }
}
