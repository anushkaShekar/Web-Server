package server;

import java.io.OutputStream;

/**
 * Defines a class that outlines the various aspects of an HTTP response 
 * Defines getter and setter methods that get and set different parts of the response
 */
public class HttpResponse {
    // Private variables
    private final OutputStream output;

    private int statusCode;
    private String description;
    private String version = "HTTP/1.1";
    private long contentLength = 0L;
    private String contentType;

    private String filePath;
    private boolean isText;

    /**
     * Defines a constructor to build an HTTP response
     * @param output is used to write the response back to the client
     */
    public HttpResponse(OutputStream output) {
        this.output = output;
    }

    /**
     * Defines a method to get the output that will write the response
     * @return output as an OutputStream
     */
    public OutputStream getOutput() {
        return output;
    }

    /**
     * Defines a method to get the status code of the response (400, 403, etc)
     * @return status code as an integer
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Defines a method to get the description of the status (ex. Bad Request, Forbidden, OK, etc)
     * @return status description as a String
     */
    public String getStatusDescription() {
        return description;
    }

    /**
     * Defines a method to get the version of the method
     * @return HTTP version as a String
     */
    public String getVersion() {
        return version;
    }

    /**
     * Defines a method to set the version for the response line
     * @param version is the version from the request
     */
    public void setVersion(String version) {
        if (version != null && version.startsWith("HTTP/")) {
            this.version = version;
        } else {
            this.version = "HTTP/1.1";
        }
    }

    /**
     * Defines a method to get the content size
     * @return content size as a long
     */
    public long getContentLength() {
        return contentLength;
    }

    /**
     * Defines a method to set the size of the requested resource
     * @param len is the size of the resource
     */
    public void setContentLength(long len) {
        this.contentLength = len;
    }

    /**
     * Defines a method that sets the status code and status description for the response line
     * @param code is the status code (ex. 400, 403, etc.)
     * @param description provides additional information about the status code (ex. 400 --> Bad Request)
     */
    public void setStatus(int code, String description) {
        this.statusCode = code;
        this.description = description;
    }

    /**
     * Defines a method to get the media type of the resource
     * @return the content type as a String
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Defines a method to set the media type of the resource
     * @param contentType is the content type of the resource
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Defines a method that gets the file path to a particular resource
     * @return the path to the resource as a String
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Defines a method that sets the file path to a particular resource
     * @param filePath is the path to the resource
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Defines a method that flags whether a resource is a text resource or a binary resource
     * @param isText is true if resource is a text file, or false if it is a binary file
     */
    public void setText(boolean isText) {
        this.isText = isText;
    }

    /**
     * Defines a method that maintains whether a resource is a text resource or a binary resource
     * @return true if text file, else false
     */
    public boolean isText() {
        return isText;
    }
}
