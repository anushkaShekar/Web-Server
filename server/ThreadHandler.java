package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Defines a class that handles the work of the threads
 */
public class ThreadHandler implements Runnable {
    private final Socket acceptedSocket;
    private final String[] args;
    private final HttpRequestParser parser;
    private final TimeoutHeuristic timeoutObj;

    /**
     * Defines a constructor that creates a new request parser object
     * @param acceptedSocket is the socket connection accepts incoming client requests
     * @param args contains the document root the client will pass in while running the server
     */
    public ThreadHandler(Socket acceptedSocket, String[] args) {
        this.acceptedSocket = acceptedSocket;
        this.args = args;
        this.parser = new HttpRequestParser();
        this.timeoutObj = new TimeoutHeuristic(acceptedSocket);
    }

    @Override
    /**
     * Defines a run method that each thread will execute
     */
    public void run() {
        try {
            // Input and Output Streams
            InputStreamReader inputStream = new InputStreamReader(acceptedSocket.getInputStream());
            BufferedReader bufferedInputStream = new BufferedReader(inputStream);
            OutputStream outputStream = acceptedSocket.getOutputStream();
            
            // Parse HTTP Request line
            HttpRequest parsedRequest = parser.parseRequest(bufferedInputStream );
            if (parsedRequest == null) {
                ResponseWriter.sendBadRequest(outputStream);
                return;
            }

            timeoutObj.startClock();

            // If path is /, then default to index.html
            HttpRequest finalParsedRequest = null;
            // getPath() returns the uri from HttpRequest.java
            if ("/".equals(parsedRequest.getPath())) {
                finalParsedRequest = new HttpRequest(parsedRequest.getMethod(), "/index.html", parsedRequest.getVersion());
            } else {
                finalParsedRequest = parsedRequest;
            }

            // Prepend the document root
            String documentRoot = null;
            if (args != null && args.length > 1) {
                documentRoot = args[1];
            } else {
                documentRoot = ".";
            }

            HttpResponse httpResponse = new HttpResponse(outputStream);
            Command command = new GetCommand(documentRoot);
            command.execute(finalParsedRequest, httpResponse);

            int timeoutLen = timeoutObj.calculateTimeout();
            acceptedSocket.setSoTimeout(timeoutLen);

        } catch (Exception e) {
            try (OutputStream output = acceptedSocket.getOutputStream()) {
                ResponseWriter.sendInternalServerError(output);
            } catch (IOException ignored) {
            }
        } finally {
            try { 
                acceptedSocket.close();
            } catch (IOException ignored){
                // This ignores exceptions if the socket has closed
            }
        }
    }
}
