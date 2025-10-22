package server;

/**
 * Defines a class which delineates the three parts of a HTTP GET request line
 */
public class HttpRequest {
   private final String requestMethod;
   private final String path;
   private final String version; 

   /**
    * Defines a constructor which sets the method, path, and version of a request
    * @param requestMethod is the type of HTTP request the client makes (ex. GET)
    * @param path is the path to the request file
    * @param version is the HTTP version that the client used to send the request (HTTP 1.0 or HTTP 1.1)
    */
   public HttpRequest(String requestMethod, String path, String version) {
        this.requestMethod = requestMethod;
        this.path = path;
        this.version = version;
   }

   /**
    * Defines a method to get the HTTP method the client is requesting
    * @return the request method as a String
    */
   public String getMethod() { 
      return requestMethod; 
   }

   /**
    * Defines a method to get the path to the file the client is requesting
    * @return the path as a String
    */
   public String getPath() { 
      return path; 
   }

   /**
    * Defines a method to get the HTTP version the client is using to send a request
    * @return the HTTP version as a String
    */
   public String getVersion() { 
      return version; 
   }
}
