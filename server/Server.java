package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Defines a class that opens a socket to accept incoming requests from a client machine
 * A thread pool is created to manage all the threads 
 */
public class Server {
    private static final int PORT = 8888;
    private static final int POOL_SIZE = 30; // Arbitrary size

    /**
     * Defines the main method which starts execution of the 
     * @param args
     */
    public static void main(String[] args) {
        Socket acceptedSocket = null;
        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);

        try (ServerSocket socket = new ServerSocket(PORT)){
            while (true) {
                acceptedSocket = socket.accept();
                executor.execute(new ThreadHandler(acceptedSocket, args));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
