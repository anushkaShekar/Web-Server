package server;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Defines a class which processes only HTTP GET requests
 */
public class GetCommand implements Command {
    private final String documentRoot;

    /**
     * Defines a constructor that sets the documentRoot to the correct path
     * @param documentRoot is set to the current directory if it is null
     */
    public GetCommand(String documentRoot) {
        if (documentRoot != null) {
            this.documentRoot = documentRoot;
        } else {
            this.documentRoot = ".";
        }
    }

    @Override
    /**
     * Defines a method which sends the appropriate response to the incoming GET request
     * @param request is converted into plain text if it contains URL-encoded characters
     * @param response is formatted with a header and a body (if applicable)
     * 
     */
    public void execute(HttpRequest request, HttpResponse response) throws Exception {
        String textPath = URLDecoder.decode(request.getPath(), StandardCharsets.UTF_8);
        File file = new File(documentRoot, textPath);

        // Sets the HTTP version in the HTTP response to the HTTP version in the parsed request
        response.setVersion(request.getVersion());

        final boolean fileNotFound = (!file.exists());
        final boolean forbiddenFile = (!file.canRead() || file.isDirectory());

        // Sends a 404 error back to the client
        if (fileNotFound) {
            ResponseWriter.sendNotFound(response);
            return;
        }

        // Sends a 403 error back to the client
        if (forbiddenFile) {
            ResponseWriter.sendForbidden(response);
            return;
        }

        // Sends a valid response back to the client
        String extension = HttpUtil.getExtension(file.getName());
        String contentType = HttpUtil.getContentType(extension);

        // Calls setters from HttpResponse.java
        response.setStatus(200, "OK");
        response.setContentLength(file.length());
        response.setContentType(contentType);
        response.setFilePath(file.getAbsolutePath());

        if (HttpUtil.getTextMimeMap().containsKey(extension)) {
            response.setText(true);
        } else {
            response.setText(false);
        }

        ResponseWriter.writeSuccessResponse(response);
    }
}
