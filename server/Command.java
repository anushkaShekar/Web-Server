package server;

/**
 * Defines an interface with a method to process incoming HTTP requests
 * Any class that implements this interface (such as GetCommand)
 * must define execute method
 */
public interface Command {

    /**
     * Defines a method that will execute a command for an incoming HTTP request and 
     * Populate the HTTP response
     * 
     * @param request is the parsed HTTP request
     * @param response is the HTTP response that is sent back to the client server
     * @throws Exception if execute() fails
     */
    void execute(HttpRequest request, HttpResponse response) throws Exception;
}
