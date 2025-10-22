package server;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Defines a class that generates and formats the response that will be sent to the client
 */
public final class ResponseWriter {
    private static final String END_LINE = "\r\n";

    private ResponseWriter() {
    }

    /**
     * Defines a method that constructs the header portion of the HTTP response
     * @param response is the HTTP parsed response
     * @throws IOException in case of failure while writing header
     */
    public static void writeHeaders(HttpResponse response) throws IOException {
        String header = HttpUtil.constructHeader(
            response.getStatusCode(),
            response.getStatusDescription(),
            response.getVersion(),
            response.getContentType(),
            response.getContentLength()
        );

        OutputStream output = response.getOutput();
        output.write(header.getBytes());
        output.write(HttpUtil.END_LINE.getBytes());
        output.flush();
    }

    /**
     * Defines a method that sets the response status for a response with no body
     * @param res is the HTTP response which will be populated in this method
     * @param statusCode is the code indicating the status of the response
     * @param description defines the status code
     * @throws IOException
     */
    private static void sendStatusNoBody(HttpResponse res, int statusCode, String description) throws IOException {
        res.setStatus(statusCode, description);
        res.setContentLength(0);
        writeHeaders(res);
    }

    /**
     * Defines a method that is called when the resource is missing
     * @param response is the HTTP response that needs to get populated
     * @throws IOException if the send doesn't work
     */
    public static void sendNotFound(HttpResponse response) throws IOException {
        sendStatusNoBody(response, 404, "Not Found");
    }

    /**
     * Defines a method that is called when the resource is forbidden to the client
     * @param response is the HTTP response that needs to get populated
     * @throws IOException if the send doesn't work
     */
    public static void sendForbidden(HttpResponse response) throws IOException {
        sendStatusNoBody(response, 403, "Forbidden");
    }

    /**
     * Defines a method that is called when the HTTP request is malformed or incorrect
     * @param output writes the response back to the client
     * @throws IOException if the send doesn't work
     */
    public static void sendBadRequest(OutputStream output) throws IOException {
        int statusCode = 400;

        String header = HttpUtil.constructHeader(
            statusCode,
            "Bad Request",
            "HTTP/1.1",
            null,
            0
        ) + END_LINE;

        // StandardCharsets.UTF_8 converts UTF encoded characters to plain text
        output.write(header.getBytes(StandardCharsets.UTF_8));
        output.write(END_LINE.getBytes(StandardCharsets.UTF_8));
        output.flush();
    }

    /**
     * 
     * @param out
     * @throws IOException
     */
    public static void sendInternalServerError(OutputStream output) throws IOException {
        int statusCode = 500;
        byte[] htmlbodyBytes = "<html<body><h1>500 Internal Server Error</h1></body></html>"
                .getBytes(StandardCharsets.UTF_8);

        String header = HttpUtil.constructHeader(
                statusCode,
                "Internal Server Error",
                "HTTP/1.1",
                "text/html",
                htmlbodyBytes.length
        ) + END_LINE;

        output.write(header.getBytes(StandardCharsets.UTF_8));
        output.write(END_LINE.getBytes(StandardCharsets.UTF_8));
        output.write(htmlbodyBytes);
        output.flush();
    }

    /**
     * Defines a method that writes the body of HTTP response if the resource is text
     * @param res is the HTTP response that will get populated
     */
    public static void writeText(HttpResponse res) {
        String path = res.getFilePath();
        if (path == null) return;

        BufferedReader reader = null;
        OutputStreamWriter writer = null;

        // BufferedReader wraps the input stream in a buffer which is faster than reading character by character
        try { reader = new BufferedReader(new InputStreamReader
            (new FileInputStream(path), StandardCharsets.UTF_8));

             writer = new OutputStreamWriter(res.getOutput(), StandardCharsets.UTF_8);

            
            char[] buffer = new char[8192];
            int count;

            // Reader will read the contents of the buffer (8192 lines at a time)
            // Until there are no more lines left (hence the -1)
            while ((count = reader.read(buffer)) != -1) {

                // Writer will write the contents of the buffer "count" lines at a time
                writer.write(buffer, 0, count);
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Defines a method that writes the body of HTTP response if the resource is binary
     * @param response is the HTTP response that will get populated
     */
    public static void writeBinary(HttpResponse response) {
        String path = response.getFilePath();
        if (path == null) return;

        try (InputStream input = new BufferedInputStream(new FileInputStream(path));
             OutputStream output = new BufferedOutputStream(response.getOutput())) {

            byte[] buffer = new byte[8192];
            int count;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Defines the method that will write the entire response (header + body) back to the client
     * @param response is the HTTP response that will get populated
     * @throws IOException if the send doesn't work
     */
    public static void writeSuccessResponse(HttpResponse response) throws IOException {
        String header = HttpUtil.constructHeader(
                response.getStatusCode(),
                response.getStatusDescription(),
                response.getVersion(),
                response.getContentType(),
                response.getContentLength()
        );

        OutputStream output = response.getOutput();
        output.write(header.getBytes(StandardCharsets.UTF_8));
        output.flush();

        if (response.getContentLength() == 0) return; 

        // Call the appropriate method depending on whether the media type is text or binary
        if (response.isText()) {
            writeText(response);
        } else {
            writeBinary(response);
        }
    }

}
