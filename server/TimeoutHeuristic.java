package server;

import java.net.Socket;

/**
 * Defines a class that supports HTTP/1.0 and 1.1 protocol
 * Develops a simple heuristic to determine the server timeout when using HTTP/1.1
 */
public class TimeoutHeuristic {

    // Private variables
    private final Socket socket;
    private long startTime = 0L;
    private long currentTime;


    /**
     * Defines a constructor that initializes a socket object
     * @param socket
     */
    TimeoutHeuristic(Socket socket) {
        this.socket = socket;
    }

    /**
     * Defines a method that gets the current time in milliseconds 
     * when the latest request is accepted by the socket
     */
    void startClock() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Defines a method that determines the timeout in milliseconds
     * @return the amount of time the server will keep an HTTP 1.1 connection alive
     */
    int calculateTimeout() {

        // If the client machine has not sent any requests, 
        // Then timeout for a default amount of time (5 seconds)
         if (startTime == 0) {
            return 5000;
         }

         // Calculate the idle time between the previous request and the latest one
         currentTime = System.currentTimeMillis();
         long idleTime = currentTime - startTime;

         // If the idle time exceeds 60 seconds then the timeout will be capped at 60 seconds
         int timeout = (int) Math.min(idleTime, 60000);

         return timeout;
    }
}
